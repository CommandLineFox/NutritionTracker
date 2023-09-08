package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealObject
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.HistoryFragment
import java.time.LocalDate

class HistoryViewModel : ViewModel() {
    lateinit var fragment: HistoryFragment
    var mealList: MutableList<MealObject> = mutableListOf()

    fun loadData() {
        ReadFromDatabase().execute()
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
            Repository.getInstance().historyList.clear()

            for (i in 7L downTo 1L) {
                val time = LocalDate.ofEpochDay(LocalDate.now().minusDays(i).toEpochDay())

                var count = 0
                mealList.forEach {
                    println(it.mealDate)
                    println(time)
                    if (it.mealDate == time) {
                        count++
                    }
                }

                Repository.getInstance().historyList.add(Entry((7 - i).toFloat(), count.toFloat()))
            }

            Repository.getInstance().historyData.value = Repository.getInstance().historyList
        }
    }
}