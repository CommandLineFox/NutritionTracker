package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (username.isNullOrEmpty()) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 700)
    }
}