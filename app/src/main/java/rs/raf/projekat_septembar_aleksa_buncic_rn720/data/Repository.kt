package rs.raf.projekat_septembar_aleksa_buncic_rn720.data

import androidx.lifecycle.MutableLiveData
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMeal

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

    var category: String? = "beef"
    var id: Long? = null

    val shortMeals: MutableList<ShortMeal> = mutableListOf()
    val shortMealList: MutableLiveData<MutableList<ShortMeal>> by lazy {
        MutableLiveData<MutableList<ShortMeal>>()
    }

    val fullMeal: MutableLiveData<FullMeal> by lazy {
        MutableLiveData<FullMeal>()
    }
}