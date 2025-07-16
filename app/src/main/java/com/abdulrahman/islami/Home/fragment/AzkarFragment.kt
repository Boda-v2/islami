package com.abdulrahman.islami.Home.fragment

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.AzkarAdapter
import com.abdulrahman.islami.R
import com.abdulrahman.islami.model.ZikrItem
import java.io.BufferedReader
import java.io.InputStreamReader

class AzkarFragment : Fragment() {

    private lateinit var btnSabah: Button
    private lateinit var btnMasaa: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AzkarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_azkar, container, false)

        btnSabah = view.findViewById(R.id.btn_sabah)
        btnMasaa = view.findViewById(R.id.btn_masaa)
        recyclerView = view.findViewById(R.id.recycler_azkar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AzkarAdapter()
        recyclerView.adapter = adapter

        btnSabah.setOnClickListener {
            loadAzkarFromAsset("azkar_sabah.txt")
        }

        btnMasaa.setOnClickListener {
            loadAzkarFromAsset("azkar_masaa.txt")
        }

        loadAzkarFromAsset("azkar_sabah.txt")
        return view
    }

    private fun loadAzkarFromAsset(fileName: String) {
        val inputStream = requireContext().assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = reader.readText()
        reader.close()

        val rawZikrBlocks = content.split("✵☾≈≈≈≈≈≈≈≈≈≈☽✵")
        val zikrList = rawZikrBlocks.mapNotNull { block ->
            val lines = block.trim().split("\n").filter { it.isNotBlank() }

            if (lines.isEmpty()) return@mapNotNull null

            val repeatRegex = Regex("(\\d+\\s?مرة|مرات|مائة|عشر|سبع)")

            val text = lines.takeWhile { !repeatRegex.containsMatchIn(it) }.joinToString("\n")
            val repeat = lines.firstOrNull { repeatRegex.containsMatchIn(it) }
            val description = lines.dropWhile { !repeatRegex.containsMatchIn(it) }
                .drop(1)
                .joinToString("\n")
                .takeIf { it.isNotBlank() }

            ZikrItem(text.trim(), repeat?.trim(), description?.trim())
        }

        adapter.submitList(zikrList)
    }
}
