package com.abdulrahman.islami.Home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.HadethAdapter
import com.abdulrahman.islami.HadethDetailsActivity
import com.abdulrahman.islami.R

class HadethFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HadethAdapter
    private val hadethTitles = mutableListOf<String>()
    private val hadethContents = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hadeth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.hadeth_RV)

        adapter = HadethAdapter(hadethTitles)
        adapter.onItemClickListener = object : HadethAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, title: String) {
                val intent = Intent(requireContext(), HadethDetailsActivity::class.java)
                intent.putExtra(Constant.EXTRA_HADETH_TITLE, title)
                intent.putExtra(Constant.EXTRA_HADETH_CONTENT, hadethContents[position])
                startActivity(intent)
            }
        }

        recyclerView.adapter = adapter

        loadHadethsFromAssets()
    }

    private fun loadHadethsFromAssets() {
        val inputStream = requireContext().assets.open("hadeths.txt")
        val content = inputStream.bufferedReader().use { it.readText() }

        val hadeths = content.split("#").map { it.trim() }.filter { it.isNotEmpty() }

        for (hadeth in hadeths) {
            val lines = hadeth.lines().filter { it.isNotBlank() }

            if (lines.isNotEmpty()) {
                val title = lines[0]
                val body = lines.subList(1, lines.size).joinToString("\n")
                hadethTitles.add(title)
                hadethContents.add(body)
            }
        }


    }
}
