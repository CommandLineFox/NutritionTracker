package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Tag
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.FilterFragment

class FilterViewModel(var mealApi: MealApi) : ViewModel() {
    lateinit var fragment: FilterFragment

    fun loadCategories() {
        Repository.getInstance().filterList.clear()
        Repository.getInstance().filterList.addAll(Repository.getInstance().categoryList)
        Repository.getInstance().filterData.value = Repository.getInstance().filterList
    }

    fun loadTags() {
        Repository.getInstance().fullMeals.clear()
        Repository.getInstance().filterList.clear()
        Repository.getInstance().mealList.forEach { meal ->
            run {
                fragment.lifecycleScope.launch {
                    val response = try {
                        mealApi.getFullMealById(meal.getId()!!)
                    } catch (e: Exception) {
                        Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    if (response.isSuccessful && response.body() != null) {
                        var contains: Boolean = false
                        Repository.getInstance().fullMeals.add(response.body()!!.meals.get(0)!!)
                        response.body()?.meals?.get(0)?.strTags?.split(",")?.forEach { tag ->
                            run {
                                contains = false
                                Repository.getInstance().filterList.forEach { filter ->
                                    run {
                                        if (filter.getTitle().equals(tag, true)) {
                                            contains = true
                                        }
                                    }
                                }

                                Repository.getInstance().filterList.add(Tag(tag))
                            }
                        }

                        Repository.getInstance().filterData.value =
                            Repository.getInstance().filterList
                    }
                }
            }
        }
    }

    fun loadAreas() {
        Repository.getInstance().filterList.clear()
        fragment.lifecycleScope.launch {
            val response = try {
                mealApi.getAreas()
            } catch (e: Exception) {
                Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Repository.getInstance().filterList.addAll(response.body()!!.meals)
                Repository.getInstance().filterData.value = Repository.getInstance().filterList
            }
        }
    }

    fun loadIngredients() {
        Repository.getInstance().filterList.clear()
        fragment.lifecycleScope.launch {
            val response = try {
                mealApi.getIngredients()
            } catch (e: Exception) {
                Toast.makeText(fragment.context, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Repository.getInstance().filterList.addAll(response.body()!!.meals)
                Repository.getInstance().filterData.value = Repository.getInstance().filterList
            }
        }
    }
}