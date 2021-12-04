package nl.inholland.android_kicokalender.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.adapter.ProfilPicAdapter
import nl.inholland.android_kicokalender.utils.StandardProfilePictures
import java.net.URI

class ProfilePicActivity : AppCompatActivity() {

    private var closePageButton: Button? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_pic)
        initWidgets()
    }

    private fun initWidgets() {
        closePageButton = findViewById(R.id.button_close_pp_popup)
        closePageButton!!.setOnClickListener {
            finish()
        }
        listView = findViewById(R.id.profile_pic_LV)
        val adapter = ProfilPicAdapter(this, StandardProfilePictures.images)
        listView!!.adapter = adapter
        listView!!.setOnItemClickListener { adapterView, view, i, l ->
            val element = adapter.getItem(i) as Int

            setResult(Activity.RESULT_OK, Intent().putExtra("image", element))
            finish()
        }
    }
}