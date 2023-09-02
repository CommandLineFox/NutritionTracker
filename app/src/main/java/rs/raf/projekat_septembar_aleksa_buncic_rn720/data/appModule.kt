import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.api.MealApi
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.CategoryViewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.ListViewModel
import rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.viewmodel.MealViewModel
import java.util.concurrent.TimeUnit

val coreModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            androidApplication().packageName,
            Context.MODE_PRIVATE
        )
    }

    single {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(MealApi::class.java)
    }

    viewModel {
        ListViewModel(get())
    }
    viewModel {
        MealViewModel(get())
    }
    viewModel {
        CategoryViewModel(get())
    }
}