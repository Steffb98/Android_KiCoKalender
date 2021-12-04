package nl.inholland.android_kicokalender.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.utils.CalendarUtils.getAuthKey

class StartActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var authToken: String = "Not set"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authToken = getAuthKey()
        checkIfLoggedIn()
        setContentView(R.layout.activity_start)
        initWidgets()
    }

    private fun checkIfLoggedIn() {
        if (authToken != "Not set")
            startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun initWidgets() {
        loginButton = findViewById(R.id.button_login)
        loginButton!!.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        registerButton = findViewById(R.id.button_register)
        registerButton!!.setOnClickListener {
            //todo: change to reg page
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}