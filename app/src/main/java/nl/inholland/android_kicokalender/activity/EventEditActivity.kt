package nl.inholland.android_kicokalender.activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.view.View
import nl.inholland.android_kicokalender.utils.CalendarUtils
import nl.inholland.android_kicokalender.utils.CalendarUtils.formattedDate
import nl.inholland.android_kicokalender.utils.CalendarUtils.formattedTime
import nl.inholland.android_kicokalender.model.Event
import nl.inholland.android_kicokalender.R
import java.time.LocalTime

class EventEditActivity : AppCompatActivity() {
    private var eventNameET: EditText? = null
    private var eventDateTV: TextView? = null
    private var eventTimeTV: TextView? = null
    private var time: LocalTime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit)
        initWidgets()
        time = LocalTime.now()
        var message: String = "Date: " + formattedDate(
            CalendarUtils.selectedDate!!
        )
        val placeHolder = time
        eventDateTV!!.text = message
        message = "Time: " + placeHolder?.let { formattedTime(it) }
        eventTimeTV!!.text = message

    }

    private fun initWidgets() {
        eventNameET = findViewById(R.id.eventNameET)
        eventDateTV = findViewById(R.id.eventDateTV)
        eventTimeTV = findViewById(R.id.eventTimeTV)
    }

    fun saveEventAction(view: View?) {
        val eventName = eventNameET!!.text.toString()
        val newEvent = Event(eventName, CalendarUtils.selectedDate!!, time!!)
        Event.eventsList.add(newEvent)
        finish()
    }
}