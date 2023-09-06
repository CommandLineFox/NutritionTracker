package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.AddToMenuFragment

class AddToMenuViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: AddToMenuFragment

    fun addToMenu() {
        if (Repository.getInstance().saveableMeal != null) {
            AddToDatabase(Repository.getInstance().saveableMeal!!).execute()
        }
    }

    fun updateMenu() {
        if (Repository.getInstance().saveableMeal != null) {
            UpdateInDatabase(Repository.getInstance().saveableMeal!!).execute()
        }
    }

    fun loadMealData(idMeal: Long) {
        fragment.lifecycleScope.launch {
            val response = try {
                mealApi.getFullMealById(idMeal)
            } catch (e: Exception) {
                Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            println(response)
            if (response.isSuccessful && response.body() != null) {
                Repository.getInstance().currentMeal = response.body()!!.meals.get(0)
                Repository.getInstance().fullMealData.value = Repository.getInstance().currentMeal
            }
        }
    }

    private inner class AddToDatabase(var mealModel: MealModel) : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val mealDao = AppDatabase.getInstance().mealDao()
            mealDao.insertMeal(mealModel)

            return null
        }
    }

    private inner class UpdateInDatabase(var mealModel: MealModel) : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val mealDao = AppDatabase.getInstance().mealDao()
            mealDao.updateMeal(mealModel)

            return null
        }
    }
}