package nl.inholland.android_kicokalender

import android.content.Context
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import nl.inholland.android_kicokalender.model.Event
import nl.inholland.android_kicokalender.utils.CalendarUtils.formattedTime

class EventAdapter(context: Context, events: List<Event?>?) :
    ArrayAdapter<Event?>(context, 0, events!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //selected date when an event is present
        var convertView = convertView
        //event position in selected date
        val event = getItem(position)
        if (convertView == null) convertView =
                //get the selected date
            LayoutInflater.from(context).inflate(R.layout.week_event_cell, parent, false)
        val eventNameCellTV = convertView!!.findViewById<TextView>(R.id.eventNameCellTV)
        val eventDateCellTV = convertView.findViewById<TextView>(R.id.eventCellFrom)
        //set event details
        eventNameCellTV.text = event!!.name
        eventDateCellTV.text = formattedTime(event.time)
        return convertView
    }
}