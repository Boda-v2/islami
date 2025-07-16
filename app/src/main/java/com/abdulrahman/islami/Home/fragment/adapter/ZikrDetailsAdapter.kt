package com.abdulrahman.islami.Home.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.R

class ZikrContentAdapter(private var content: List<String>) :
    RecyclerView.Adapter<ZikrContentAdapter.ViewHolder>() {

    fun updateContent(newContent: List<String>) {
        content = newContent
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView: TextView = view.findViewById(R.id.content_line)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_zikr, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contentTextView.text = content[position]
    }
}
