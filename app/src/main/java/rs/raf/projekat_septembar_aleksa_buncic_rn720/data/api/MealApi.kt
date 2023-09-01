package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.FullMealResponse
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model.ShortMealResponse

interface MealApi {
    @GET("api/json/v1/1/filter.php?")
    suspend fun getMealByCategory(@Query("c") c: String): Response<ShortMealResponse>

    @GET("api/json/v1/1/lookup.php?")
    suspend fun getFullMealById(@Query("i") i: Long): Response<FullMealResponse>
}