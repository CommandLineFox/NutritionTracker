package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MealDao {
    @Query("SELECT * FROM meals")
    fun getMeals(): List<MealModel>

    @Insert
    fun insertMeal(mealModel: MealModel)

    @Delete
    fun deleteMeal(mealModel: MealModel)

    @Update
    fun updateMeal(mealModel: MealModel)
}