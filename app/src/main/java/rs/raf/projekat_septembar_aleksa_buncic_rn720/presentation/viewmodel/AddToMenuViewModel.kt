package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.AddToMenuFragment

class AddToMenuViewModel : ViewModel() {
    lateinit var fragment: AddToMenuFragment

    fun addToMenu() {
        if (Repository.getInstance().saveableMeal != null) {
            AddToDatabase(Repository.getInstance().saveableMeal!!).execute()
        }
    }

    private inner class AddToDatabase(var mealModel: MealModel) : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val mealDao = AppDatabase.getInstance().mealDao()
            mealDao.insertMeal(mealModel)

            return null
        }
    }
}