package com.abdulrahman.islami.Home.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.R

class HadethContentAdapter(private var lines: List<String>) :
    RecyclerView.Adapter<HadethContentAdapter.ViewHolder>() {

    fun updateContent(newLines: List<String>) {
        lines = newLines
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_hadeth, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lines.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.line.text = lines[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val line: TextView = itemView.findViewById(R.id.hadeth_line)
    }
}
