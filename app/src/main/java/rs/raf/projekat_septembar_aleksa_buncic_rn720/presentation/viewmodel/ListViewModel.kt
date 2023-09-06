package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealObject
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMealResponse
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMealResponse
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment

class ListViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: ListFragment
    var mealList: MutableList<IMeal> = mutableListOf()
    fun loadData() {
        Repository.getInstance().mealList.clear()

        if (Repository.getInstance().isMealFromApi == 0) {
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
        } else if (Repository.getInstance().isMealFromApi == 1) {
            ReadFromDatabase().execute()
        }
    }

    fun deleteFromDatabase() {
        if (Repository.getInstance().currentMeal != null) {
            if (Repository.getInstance().currentMeal is MealObject) {
                val currMeal = Repository.getInstance().currentMeal as MealObject
                val meal = MealModel(currMeal.uid, currMeal.getId()!!, currMeal.mealDate.toEpochDay() * 86400, currMeal.mealType, Gson().toJson(currMeal.meal))
                DeleteFromDatabase(meal).execute()
            }
        }
    }

    @SuppressLint("IntentReset")
    fun sendEmail(recipient: String) {
        val subject = "Plan"
        val message = formatMessage()
        val intent = Intent(Intent.ACTION_SEND)

        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            fragment.activity?.startActivity(Intent.createChooser(intent, "Choose email client"))
        } catch (e: Exception) {
            Toast.makeText(fragment.requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatMessage(): String {
        return ""
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

    private inner class DeleteFromDatabase(var mealModel: MealModel) : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val mealDao = AppDatabase.getInstance().mealDao()
            mealDao.deleteMeal(mealModel)

            return null
        }
    }
}