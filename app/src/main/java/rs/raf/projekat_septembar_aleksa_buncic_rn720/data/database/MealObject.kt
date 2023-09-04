package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database

import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import java.time.LocalDate

data class MealObject(var uid: Long, var mealDate: LocalDate, var mealType: String, var meal: FullMeal) : IMeal {
    override fun getId(): Long? {
        return meal.idMeal
    }

    override fun getTitle(): String? {
        return meal.strMeal
    }

    override fun getImage(): String? {
        return meal.strMealThumb
    }

    override fun getDate(): String? {
        return mealDate.toString()
    }

    override fun getType(): String? {
        return mealType
    }
}
