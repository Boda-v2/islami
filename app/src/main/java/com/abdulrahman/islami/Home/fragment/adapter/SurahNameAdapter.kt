package com.abdulrahman.islami.Home.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.R

class SurahNameAdapter(
    val items: List<String>,
    val ayahCounts: List<Int>,
    val favorites: Set<String>
) : RecyclerView.Adapter<SurahNameAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    var onFavClickListener: ((String) -> Unit)? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, surahName: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.surah_name)
        val count: TextView = itemView.findViewById(R.id.ayah_count)
        val favIcon: ImageView = itemView.findViewById(R.id.fav_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_verses, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surahName = items[position]
        val count = ayahCounts[position]

        holder.name.text = surahName
        holder.count.text = "(${count} آية)"

        val isFav = favorites.contains(surahName)
        holder.favIcon.setImageResource(
            if (isFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
        )

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, surahName)
        }

        holder.favIcon.setOnClickListener {
            onFavClickListener?.invoke(surahName)
        }
    }
}
