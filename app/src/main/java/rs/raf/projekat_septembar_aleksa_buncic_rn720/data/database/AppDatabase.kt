package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.raf.projekat_septembar_aleksa_buncic_rn720.MyApplication

@Database(entities = [MealModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(MyApplication.appContext, AppDatabase::class.java, "meals").build()
                    }
                }
            }

            return instance!!
        }
    }

    abstract fun mealDao(): MealDao
}