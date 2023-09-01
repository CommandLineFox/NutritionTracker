package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.ListFragment

class ListViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: ListFragment

    fun loadData() {
        Repository.getInstance().shortMeals.clear()
        fragment.lifecycleScope.launch {
            val response = try {
                mealApi.getMealByCategory(Repository.getInstance().category!!)
                //TODO
            } catch (e: Exception) {
                Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT)
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Repository.getInstance().shortMeals.addAll(response.body()!!.meals)
                Repository.getInstance().shortMealList.value = Repository.getInstance().shortMeals
            }
        }
    }
}