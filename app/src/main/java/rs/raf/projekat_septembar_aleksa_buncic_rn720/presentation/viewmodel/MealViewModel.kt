package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.MealFragment

class MealViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: MealFragment

    fun loadData() {
        if (Repository.getInstance().isMealFromApi) {
            fragment.lifecycleScope.launch {
                val response = try {
                    if (Repository.getInstance().id != null) {
                        mealApi.getFullMealById(Repository.getInstance().id!!)
                    } else {
                        mealApi.getFullMealById(1)
                    }
                } catch (e: Exception) {
                    Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    if (Repository.getInstance().id != null) {
                        Repository.getInstance().fullMealData.value = response.body()!!.meals.get(0)
                    }
                }
            }
        }
    }
}