package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMeal
import java.time.LocalDate

@Entity(tableName = "meals")
class MealModel(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "meal_id") var mealId: Long,
    @ColumnInfo(name = "meal_date") var mealDate: Long,
    @ColumnInfo(name = "meal_type") var mealType: String,
    @ColumnInfo(name = "meal_content") var content: String
) {
    @Ignore
    fun convertToMeal() = MealObject(uid, LocalDate.ofEpochDay(mealDate), mealType, Gson().fromJson(content, FullMeal::class.java))

    @Ignore
    override fun toString(): String {
        return mealDate.toString() + "\n" + content
    }
}