package com.abdulrahman.islami.Home.fragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.R

class VersesAdapter : RecyclerView.Adapter<VersesAdapter.ViewHolder>() {

    var items: List<String>? = null
    var bookmarkPosition: Int = -1
    var onLongClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_verses, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = items?.get(position)
        val verseNumber = position + 1
        holder.content.text = "($verseNumber) $content"

        // إذا كانت هذه هي الآية المرجعية
        if (position == bookmarkPosition) {
            holder.content.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.bookmark_highlight)
            )
        } else {
            holder.content.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(position)
            true
        }
    }

    fun changeData(verses: List<String>) {
        this.items = verses
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.content)
    }
}
