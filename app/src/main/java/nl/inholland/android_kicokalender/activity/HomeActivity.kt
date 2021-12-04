package nl.inholland.android_kicokalender.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.inholland.android_kicokalender.utils.CalendarUtils
import nl.inholland.android_kicokalender.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setNavBarSettings()
        initWidget()
    }

    private fun initWidget() {
        findViewById<Button>(R.id.profile_button).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setNavBarSettings() {
        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        CalendarUtils.setNavBarSettings(bottomNavBar, this)
    }
}