package rs.raf.projekat_septembar_aleksa_buncic_rn720.data

import androidx.lifecycle.MutableLiveData
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database.MealObject
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.Category
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IFilter
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal

class Repository private constructor() {
    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(): Repository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Repository()
                    }
                }
            }
            return instance!!
        }
    }

    var isMealFromApi = 0

    var area: String? = null
    var ingredient: String? = null
    var category: String? = null
    var tag: String? = null
    var search: String? = null

    var id: Long? = null

    val fullMeals: MutableList<FullMeal> = mutableListOf()
    val fullMealData: MutableLiveData<IMeal> by lazy {
        MutableLiveData<IMeal>()
    }

    val mealList: MutableList<IMeal> = mutableListOf()
    val mealData: MutableLiveData<MutableList<IMeal>> by lazy {
        MutableLiveData<MutableList<IMeal>>()
    }

    val categoryList: MutableList<Category> = mutableListOf()
    val categoryData: MutableLiveData<MutableList<Category>> by lazy {
        MutableLiveData<MutableList<Category>>()
    }

    val filterList: MutableList<IFilter> = mutableListOf()
    val filterData: MutableLiveData<MutableList<IFilter>> by lazy {
        MutableLiveData<MutableList<IFilter>>()
    }

    val planList: MutableList<MealObject> = mutableListOf()
    val planData: MutableLiveData<MutableList<MealObject>> by lazy {
        MutableLiveData<MutableList<MealObject>>()
    }

    var currentMeal: IMeal? = null
    var saveableMeal: MealModel? = null
    var planMeal: MealObject? = null

    var addingToMenu: Boolean = false
    var addingToPlan: Boolean = false
    var editingInMenu: Boolean = false

    var sortListAscending: Boolean = true
    var sortFilterAscending: Boolean = true
}