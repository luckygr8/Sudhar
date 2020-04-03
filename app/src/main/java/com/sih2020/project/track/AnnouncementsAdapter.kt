package com.sih2020.project.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sih2020.project.R

class AnnouncementsAdapter(
    private val announcements: ArrayList<Announcement>
) : RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var message: TextView = itemView.findViewById(R.id.announcement_message)
        var date: TextView = itemView.findViewById(R.id.announcement_date)
        var rep: TextView = itemView.findViewById(R.id.announcement_rep)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.announcement, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = announcements.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = announcements[position].message
        holder.date.text = announcements[position].date
        holder.rep.text = announcements[position].rep
    }
}