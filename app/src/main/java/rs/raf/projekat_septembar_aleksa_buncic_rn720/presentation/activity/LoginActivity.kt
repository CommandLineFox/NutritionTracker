package rs.raf.projekat_septembar_aleksa_buncic_rn720.presentation.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import rs.raf.projekat_septembar_aleksa_buncic_rn720.R

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginUsername: EditText
    private lateinit var activityLoginPassword: EditText
    private lateinit var activityLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initListener()
    }

    private fun initView() {
        activityLoginUsername = findViewById(R.id.activityLoginUsername)
        activityLoginPassword = findViewById(R.id.activityLoginPassword)
        activityLoginButton = findViewById(R.id.activityLoginButton)
    }

    private fun initListener() {
        var username: String
        var password: String

        activityLoginButton!!.setOnClickListener {
            username = activityLoginUsername!!.text.toString()
            password = activityLoginPassword!!.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username and password cannot be left empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else if (password.length < 5) {
                Toast.makeText(
                    this,
                    "Password cannot be less than 5 characters long",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (password != "Stinky") {
                Toast.makeText(
                    this,
                    "Incorrect password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MainActivity::class.java)
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            sharedPreferences.edit().putString("username", username).apply()
            startActivity(intent)
            finish()
        }
    }
}