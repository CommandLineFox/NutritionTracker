package rs.raf.projekat_septembar_aleksa_buncic_rn720

import android.app.Application
import android.content.Context
import coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.appContext = applicationContext
        startKoin {
            androidContext(this@MyApplication)
            modules(coreModule)
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}