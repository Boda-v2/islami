package com.abdulrahman.islami.Home.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.R
import com.abdulrahman.islami.model.ZikrItem

class AzkarAdapter : RecyclerView.Adapter<AzkarAdapter.ViewHolder>() {

    private var items: List<ZikrItem> = emptyList()

    fun submitList(newList: List<ZikrItem>) {
        items = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.zikr_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_zikr, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.text // ملخص الذكر
    }

    override fun getItemCount(): Int = items.size
}
