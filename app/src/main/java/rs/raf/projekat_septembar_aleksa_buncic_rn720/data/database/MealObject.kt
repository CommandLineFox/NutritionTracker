package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database

import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.IMeal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        return mealDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    override fun getType(): String? {
        return mealType
    }

    override fun getFullMeal(): FullMeal? {
        return meal
    }
}
