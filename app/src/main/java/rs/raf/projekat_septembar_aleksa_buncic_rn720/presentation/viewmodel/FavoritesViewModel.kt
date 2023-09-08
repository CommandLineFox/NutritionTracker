package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.AppDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealObject
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FavoriteArea
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FavoriteCategory
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FavoriteMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.fragment.FavoritesFragment

class FavoritesViewModel : ViewModel() {
    lateinit var fragment: FavoritesFragment
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
            Repository.getInstance().databaseMeals.clear()
            Repository.getInstance().favoriteAreas.clear()
            Repository.getInstance().favoriteCategories.clear()
            Repository.getInstance().favoriteMeals.clear()

            Repository.getInstance().databaseMeals.addAll(mealList)

            mealList.forEach {
                var oldArea: FavoriteArea? = null
                var newArea: FavoriteArea? = null

                if (Repository.getInstance().favoriteAreas.isEmpty()) {
                    val favoriteArea = FavoriteArea(it.meal.strArea!!)
                    favoriteArea.uses++
                    Repository.getInstance().favoriteAreas.add(favoriteArea)
                } else {
                    oldArea = null
                    newArea = null

                    Repository.getInstance().favoriteAreas.forEach { area ->
                        run {
                            if (area.getName() == it.meal.strArea) {
                                oldArea = area
                                newArea = area
                                newArea!!.uses++
                            }
                        }
                    }

                    if (oldArea == null) {
                        val favoriteArea = FavoriteArea(it.meal.strArea!!)
                        favoriteArea.uses++
                        Repository.getInstance().favoriteAreas.add(favoriteArea)
                    } else {
                        Repository.getInstance().favoriteAreas.remove(oldArea)
                        Repository.getInstance().favoriteAreas.add(newArea!!)

                        Repository.getInstance().favoriteAreas.sortByDescending { favoriteArea ->
                            favoriteArea.uses
                        }
                    }
                }
            }

            mealList.forEach {
                var oldCategory: FavoriteCategory? = null
                var newCategory: FavoriteCategory? = null

                if (Repository.getInstance().favoriteCategories.isEmpty()) {
                    val favoriteCategory = FavoriteCategory(it.meal.strCategory!!)
                    favoriteCategory.uses++
                    Repository.getInstance().favoriteCategories.add(favoriteCategory)
                } else {
                    oldCategory = null
                    newCategory = null

                    Repository.getInstance().favoriteCategories.forEach { category ->
                        run {
                            if (category.getName() == it.meal.strCategory) {
                                oldCategory = category
                                newCategory = category
                                newCategory!!.uses++
                            }
                        }
                    }

                    if (oldCategory == null) {
                        val favoriteCategory = FavoriteCategory(it.meal.strCategory!!)
                        favoriteCategory.uses++
                        Repository.getInstance().favoriteCategories.add(favoriteCategory)
                    } else {
                        Repository.getInstance().favoriteCategories.remove(oldCategory)
                        Repository.getInstance().favoriteCategories.add(newCategory!!)

                        Repository.getInstance().favoriteCategories.sortByDescending { favoriteCategory ->
                            favoriteCategory.uses
                        }
                    }
                }
            }

            mealList.forEach {
                var oldMeal: FavoriteMeal? = null
                var newMeal: FavoriteMeal? = null

                if (Repository.getInstance().favoriteMeals.isEmpty()) {
                    val favoriteMeal = FavoriteMeal(it.meal)
                    favoriteMeal.uses++
                    Repository.getInstance().favoriteMeals.add(favoriteMeal)
                } else {
                    oldMeal = null
                    newMeal = null

                    Repository.getInstance().favoriteMeals.forEach { meal ->
                        run {
                            if (meal.meal.getId() == it.meal.idMeal) {
                                oldMeal = meal
                                newMeal = meal
                                newMeal!!.uses++
                            }
                        }
                    }

                    if (oldMeal == null) {
                        val favoriteMeal = FavoriteMeal(it.meal)
                        favoriteMeal.uses++
                        Repository.getInstance().favoriteMeals.add(favoriteMeal)
                    } else {
                        Repository.getInstance().favoriteMeals.remove(oldMeal)
                        Repository.getInstance().favoriteMeals.add(newMeal!!)

                        Repository.getInstance().favoriteMeals.sortByDescending { favoriteMeal ->
                            favoriteMeal.uses
                        }
                    }
                }
            }

            when (Repository.getInstance().favoriteSelected) {
                0 -> Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteAreas.toMutableList()
                1 -> Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteCategories.toMutableList()
                2 -> Repository.getInstance().favoriteData.value = Repository.getInstance().favoriteMeals.toMutableList()
            }
        }
    }
}