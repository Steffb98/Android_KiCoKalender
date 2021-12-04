package nl.inholland.android_kicokalender.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import nl.inholland.android_kicokalender.R

class ProfilPicAdapter(private val context: Activity, private val imageIdList: Array<Int>): BaseAdapter() {
    override fun getCount(): Int {
        return imageIdList.size
    }

    override fun getItem(p0: Int): Any {
        return imageIdList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.profile_picture, null)
        val imageView = rowView.findViewById< ImageView>(R.id.profile_pic)
        imageView.setImageResource(imageIdList[p0])
        return rowView
    }
}