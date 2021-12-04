package nl.inholland.android_kicokalender.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.inholland.android_kicokalender.activity.MonthViewActivity
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.activity.HomeActivity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.ArrayList

//some project-wide utilities
object CalendarUtils {
    @JvmField
    //is set when opening month/weekview
    var selectedDate: LocalDate? = null

    @JvmStatic
    fun formattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    @JvmStatic
    fun formattedTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return time.format(formatter)
    }

    @JvmStatic
    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    //to receive the existing days in a specific month
    @JvmStatic
    fun daysInMonthArray(date: LocalDate?): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(date)
        //size of specific month
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        //generating the boxes to be shown in monthview
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(null) else
                daysInMonthArray.add(
                    LocalDate.of(
                        selectedDate!!.year, selectedDate!!.month, i - dayOfWeek
                    )
                )
        }
        return daysInMonthArray
    }

    //generating the current week
    @JvmStatic
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = sundayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    //checking what date the last sunday was (start of week)
    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.SUNDAY) return current
            current = current.minusDays(1)
        }
        return null
    }

    fun setNavBarSettings(bottomNavBar: BottomNavigationView, context: Activity) {
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                    true
                }
                R.id.bottom_nav_calendar -> {
                    context.startActivity(Intent(context, MonthViewActivity::class.java))
                    true
                }
                R.id.bottom_nav_files -> {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                    true
                }
                R.id.bottom_nav_gallery -> {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                    true
                }
                R.id.bottom_nav_payments -> {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    fun Activity.getAuthKey(): String {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("AUTH_TOKEN_KEY", "Not set").toString()
    }
}