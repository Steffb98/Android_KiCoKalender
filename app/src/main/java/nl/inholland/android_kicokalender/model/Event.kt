package nl.inholland.android_kicokalender.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.ArrayList

class Event(var name: String, var date: LocalDate, var time: LocalTime) {

    //static object that can be used project-wide
    companion object {
        //eventslist contains all added events
        var eventsList = ArrayList<Event>()
        fun eventsForDate(date: LocalDate): ArrayList<Event?> {
            val events = ArrayList<Event?>()
            for (event in eventsList) {
                if (event.date == date) events.add(event)
            }
            return events
        }
    }
}