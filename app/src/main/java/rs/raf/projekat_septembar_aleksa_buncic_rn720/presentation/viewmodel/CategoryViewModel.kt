package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.CategoryFragment

class CategoryViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: CategoryFragment

    fun loadData() {
        Repository.getInstance().categoryList.clear()
        fragment.lifecycleScope.launch {
            val response = try {
                mealApi.getCategories()
            } catch (e: Exception) {
                Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Repository.getInstance().categoryList.addAll(response.body()!!.categories)
                Repository.getInstance().categoryData.value = Repository.getInstance().categoryList
            }
        }
    }
}