package nl.inholland.android_kicokalender.activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import android.widget.ListView
import nl.inholland.android_kicokalender.*
import nl.inholland.android_kicokalender.adapter.CalendarAdapter
import nl.inholland.android_kicokalender.model.Event
import nl.inholland.android_kicokalender.utils.CalendarUtils.daysInWeekArray
import nl.inholland.android_kicokalender.utils.CalendarUtils.monthYearFromDate
import nl.inholland.android_kicokalender.model.Event.Companion.eventsForDate
import nl.inholland.android_kicokalender.utils.CalendarUtils
import java.time.LocalDate
import java.util.ArrayList

class WeekViewActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var eventListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        initWidgets()
        setWeekView()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
        eventListView = findViewById(R.id.weekEventListView)
    }

    private fun setWeekView() {
        //textview selected week
        monthYearText!!.text = monthYearFromDate(CalendarUtils.selectedDate!!)
        //all dates for selected week
        val days = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this, applicationContext)
        //setting the layout to 7 columns
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
        setEventAdapter()
    }

    //buttonaction  for precious
    fun previousWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusWeeks(1)
        setWeekView()
    }

    //buttonaction for next
    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusWeeks(1)
        setWeekView()
    }

    //buttonaction for selected date to set it to the selected date and refreshing the view to make the day gray
    override fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }

    //when an event is added resume this view and refresh the adapter to update events per day
    override fun onResume() {
        super.onResume()
        setEventAdapter()
    }

    private fun setEventAdapter() {
        //getting all events for specific date
        val dailyEvents: ArrayList<Event?> = eventsForDate(
            CalendarUtils.selectedDate!!
        )
        //set adapter with (new) events
        val eventAdapter = EventAdapter(applicationContext, dailyEvents)
        eventListView!!.adapter = eventAdapter
    }

    fun newEventAction(view: View?) {
        startActivity(Intent(this, EventEditActivity::class.java))
    }

    //buttonaction for monthly overview
    fun monthlyAction(view: android.view.View) {
        startActivity(Intent(this, MonthViewActivity::class.java))
    }
}