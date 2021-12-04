package nl.inholland.android_kicokalender.adapter

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import nl.inholland.android_kicokalender.utils.CalendarUtils
import nl.inholland.android_kicokalender.CalendarViewHolder
import nl.inholland.android_kicokalender.model.Event
import nl.inholland.android_kicokalender.R
import java.time.LocalDate
import java.util.ArrayList

internal class CalendarAdapter(
    private val days: ArrayList<LocalDate?>,
    private val onItemListener: OnItemListener,
    private val context: Context
) : RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        if (days.size > 15) //month view
            layoutParams.height = (parent.height * 0.166666666).toInt() else  // week view
            layoutParams.height = parent.height
        return CalendarViewHolder(view, onItemListener, days)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]

        if (date == null) //not a calendar day
            holder.dayOfMonth.text = "" else { //an existing date
            holder.dayOfMonth.text = date.dayOfMonth.toString()

            val dailyEvents: ArrayList<Event?> = Event.eventsForDate(
                date
            )

            fillDailyEvent(dailyEvents, holder)

            if (date == CalendarUtils.selectedDate) //if the date is selected
                holder.parentView.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun fillDailyEvent(dailyEvents: ArrayList<Event?>, holder: CalendarViewHolder) {
        var i = 0

        for (event in dailyEvents) {
            if (i <= 2) {
                if (event == dailyEvents[i]) {
                    when (i) {
                        0 -> {
                            fillEventCell(event, holder.event1)
                        }
                        1 -> {
                            fillEventCell(event, holder.event2)
                        }
                        2 -> {
                            fillEventCell(event, holder.event3)
                        }
                    }
                }
                i++
            }
        }
    }

    private fun fillEventCell(event: Event?, tv: TextView) {
        tv.text = event?.name
        tv.visibility = View.VISIBLE
        tv.gravity = View.TEXT_ALIGNMENT_GRAVITY
        tv.setBackgroundColor(context.getColor(R.color.blue))
    }


    override fun getItemCount(): Int {
        return days.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }
}