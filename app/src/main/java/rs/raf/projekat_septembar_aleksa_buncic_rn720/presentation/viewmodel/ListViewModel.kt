package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMealResponse
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMealResponse
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment

class ListViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: ListFragment
    var mealList: MutableList<IMeal> = mutableListOf()
    fun loadData() {
        Repository.getInstance().mealList.clear()

        if (Repository.getInstance().isMealFromApi) {
            if (Repository.getInstance().tag != null) {
                Repository.getInstance().fullMeals.forEach { meal ->
                    run {
                        if (!meal.strTags.isNullOrEmpty() && meal.strTags.contains(Repository.getInstance().tag!!, true)) {
                            Repository.getInstance().mealList.add(meal)
                        }
                    }
                }

                Repository.getInstance().mealData.value = Repository.getInstance().mealList
            } else {
                fragment.lifecycleScope.launch {
                    val response = try {
                        if (Repository.getInstance().category != null) {
                            mealApi.getMealByCategory(Repository.getInstance().category!!)
                        } else if (Repository.getInstance().search != null) {
                            mealApi.getSearch(Repository.getInstance().search!!)
                        } else if (Repository.getInstance().area != null) {
                            mealApi.getSearch(Repository.getInstance().search!!)
                        } else if (Repository.getInstance().ingredient != null) {
                            mealApi.getSearch(Repository.getInstance().search!!)
                        } else {
                            return@launch
                        }
                    } catch (e: Exception) {
                        Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    if (response.isSuccessful && response.body() != null) {
                        if (response.body() is ShortMealResponse) {
                            val meals = response.body() as ShortMealResponse
                            Repository.getInstance().mealList.addAll(meals.meals)
                        }
                        if (response.body() is FullMealResponse) {
                            val meals = response.body() as FullMealResponse
                            if (meals.meals.isNotEmpty()) {
                                Repository.getInstance().mealList.addAll(meals.meals)
                            }
                        }
                        Repository.getInstance().mealData.value =
                            Repository.getInstance().mealList
                    }
                }
            }
        } else {
            ReadFromDatabase().execute()
        }
    }

    fun planButtonClicked() {
        //TODO
    }


    private inner class ReadFromDatabase() : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val mealDao = AppDatabase.getInstance().mealDao()
            val response = mealDao.getMeals()

            mealList.clear()
            response.forEach {
                mealList.add(it.convertToMeal())
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            Repository.getInstance().mealList.addAll(mealList)
            Repository.getInstance().mealData.value = Repository.getInstance().mealList
        }
    }
}