package com.abdulrahman.islami.Home.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.abdulrahman.islami.R

class SebhaFragment : Fragment() {

    private lateinit var tvCount: TextView
    private lateinit var tvZikr: TextView
    private lateinit var btnCount: Button
    private lateinit var btnReset: Button
    private lateinit var btnNext: Button

    private val azkar = listOf("سبحان الله", "الحمد لله", "الله أكبر")
    private var currentZikrIndex = 0
    private var currentCount = 0

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sebha, container, false)

        tvCount = view.findViewById(R.id.tv_count)
        tvZikr = view.findViewById(R.id.tv_zikr)
        btnCount = view.findViewById(R.id.btn_count)
        btnReset = view.findViewById(R.id.btn_reset)
        btnNext = view.findViewById(R.id.btn_next)

        sharedPreferences = requireActivity().getSharedPreferences("tasbih_prefs", Context.MODE_PRIVATE)

        updateZikrUI()

        btnCount.setOnClickListener {
            currentCount++
            updateCountDisplay()
            saveCurrentCount()
        }

        btnReset.setOnClickListener {
            currentCount = 0
            updateCountDisplay()
            saveCurrentCount()
        }

        btnNext.setOnClickListener {
            saveCurrentCount()
            currentZikrIndex = (currentZikrIndex + 1) % azkar.size
            updateZikrUI()
        }

        return view
    }

    private fun updateZikrUI() {
        val currentZikr = azkar[currentZikrIndex]
        tvZikr.text = currentZikr
        currentCount = sharedPreferences.getInt(currentZikr, 0)
        updateCountDisplay()
    }

    private fun updateCountDisplay() {
        tvCount.text = currentCount.toString()
    }

    private fun saveCurrentCount() {
        sharedPreferences.edit()
            .putInt(azkar[currentZikrIndex], currentCount)
            .apply()
    }
}
