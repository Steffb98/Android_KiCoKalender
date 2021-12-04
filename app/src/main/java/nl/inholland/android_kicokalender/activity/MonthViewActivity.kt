package nl.inholland.android_kicokalender.activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.utils.CalendarUtils.daysInMonthArray
import nl.inholland.android_kicokalender.utils.CalendarUtils.monthYearFromDate
import nl.inholland.android_kicokalender.adapter.CalendarAdapter
import nl.inholland.android_kicokalender.utils.CalendarUtils
import java.time.LocalDate

class MonthViewActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_view)
        initWidgets()
        //setting the selected date to today when opening the month view
        CalendarUtils.selectedDate = LocalDate.now()
        setNavBarSettings()
        setMonthView()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)
    }

    private fun setMonthView() {
        monthYearText!!.text =
            monthYearFromDate(CalendarUtils.selectedDate!!)
        //count of day in month that is viewed in month/weekview
        val daysInMonth = daysInMonthArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this, applicationContext)
        //setting the layout to 7 days per row
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    //buttonaction to previous
    fun previousMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    //buttonaction to next
    fun nextMonthAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    //when clicking a day set the selected date and reset the view to make the selected day gray
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            startActivity(Intent(this, WeekViewActivity::class.java))
        }
    }

    //buttonaction for weekly
    fun weeklyAction(view: View) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    fun menuButton(view: android.view.View) {
        startActivity(Intent(this, WeekViewActivity::class.java))
    }

    private fun setNavBarSettings() {
        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        CalendarUtils.setNavBarSettings(bottomNavBar,this)
    }
}