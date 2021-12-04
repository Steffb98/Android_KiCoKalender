package nl.inholland.android_kicokalender.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import nl.inholland.android_kicokalender.R

class ProfileActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initWidgets()
    }

    private fun initWidgets() {
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        sharedPreferences!!.edit().remove("AUTH_TOKEN_KEY").apply()
        startActivity(Intent(this, StartActivity::class.java))
    }
}