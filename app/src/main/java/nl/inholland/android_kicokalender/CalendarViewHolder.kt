package nl.inholland.android_kicokalender

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import nl.inholland.android_kicokalender.adapter.CalendarAdapter
import java.time.LocalDate
import java.util.ArrayList

class CalendarViewHolder internal constructor(
    itemView: View,
    private val onItemListener: CalendarAdapter.OnItemListener,
    days: ArrayList<LocalDate?>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val days: ArrayList<LocalDate?>

    //select the object that should be changed
    val parentView: View = itemView.findViewById(R.id.parentView)
    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    val event1: TextView = itemView.findViewById(R.id.cellDayEvent1)
    val event2: TextView = itemView.findViewById(R.id.cellDayEvent2)
    val event3: TextView = itemView.findViewById(R.id.cellDayEvent3)

    //overridden function that saves the selected date
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }

    init {
        itemView.setOnClickListener(this)
        this.days = days
    }
}