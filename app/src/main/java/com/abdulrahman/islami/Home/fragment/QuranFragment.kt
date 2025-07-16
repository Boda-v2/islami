package com.abdulrahman.islami.Home.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.SurahNameAdapter
import com.abdulrahman.islami.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial
import java.io.BufferedReader

class QuranFragment : Fragment() {

    val surahNames = listOf(
        "الفاتحه", "البقرة", "آل عمران", "النساء", "المائدة", "الأنعام", "الأعراف", "الأنفال",
        "التوبة", "يونس", "هود", "يوسف", "الرعد", "إبراهيم", "الحجر", "النحل", "الإسراء", "الكهف",
        "مريم", "طه", "الأنبياء", "الحج", "المؤمنون", "النّور", "الفرقان", "الشعراء", "النّمل",
        "القصص", "العنكبوت", "الرّوم", "لقمان", "السجدة", "الأحزاب", "سبأ", "فاطر", "يس", "الصافات",
        "ص", "الزمر", "غافر", "فصّلت", "الشورى", "الزخرف", "الدّخان", "الجاثية", "الأحقاف", "محمد",
        "الفتح", "الحجرات", "ق", "الذاريات", "الطور", "النجم", "القمر", "الرحمن", "الواقعة", "الحديد",
        "المجادلة", "الحشر", "الممتحنة", "الصف", "الجمعة", "المنافقون", "التغابن", "الطلاق", "التحريم",
        "الملك", "القلم", "الحاقة", "المعارج", "نوح", "الجن", "المزّمّل", "المدّثر", "القيامة", "الإنسان",
        "المرسلات", "النبأ", "النازعات", "عبس", "التكوير", "الإنفطار", "المطفّفين", "الإنشقاق",
        "البروج", "الطارق", "الأعلى", "الغاشية", "الفجر", "البلد", "الشمس", "الليل", "الضحى", "الشرح",
        "التين", "العلق", "القدر", "البينة", "الزلزلة", "العاديات", "القارعة", "التكاثر", "العصر",
        "الهمزة", "الفيل", "قريش", "الماعون", "الكوثر", "الكافرون", "النصر", "المسد", "الإخلاص",
        "الفلق", "الناس"
    )

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SurahNameAdapter
    lateinit var ayahCounts: List<Int>
    lateinit var favorites: MutableSet<String>
    var showOnlyFavorites = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.sura_RV)
        val fabLastOpened = view.findViewById<FloatingActionButton>(R.id.fab_last_surah)
        val switchFav = view.findViewById<SwitchMaterial>(R.id.switch_favorite)

        val prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        favorites = prefs.getStringSet("favorites", mutableSetOf())!!.toMutableSet()

        ayahCounts = surahNames.mapIndexed { index, _ ->
            try {
                val file = "quran/${index + 1}.txt"
                val input = requireContext().assets.open(file)
                BufferedReader(input.reader()).readLines().count { it.isNotBlank() }
            } catch (e: Exception) {
                0
            }
        }

        fun getVisibleSuras(): List<String> {
            return if (showOnlyFavorites) surahNames.filter { favorites.contains(it) } else surahNames
        }

        fun getVisibleAyahCounts(): List<Int> {
            return if (showOnlyFavorites)
                surahNames.withIndex().filter { favorites.contains(it.value) }.map { ayahCounts[it.index] }
            else
                ayahCounts
        }

        fun updateAdapter() {
            adapter = SurahNameAdapter(getVisibleSuras(), getVisibleAyahCounts(), favorites)
            adapter.onItemClickListener = object : SurahNameAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, surahName: String) {
                    val realIndex = surahNames.indexOf(surahName)
                    prefs.edit().putInt("last_surah_index", realIndex).apply()
                    showSurahDetails(realIndex, surahName)
                }
            }
            adapter.onFavClickListener = {
                if (favorites.contains(it)) favorites.remove(it) else favorites.add(it)
                prefs.edit().putStringSet("favorites", favorites).apply()
                updateAdapter()
            }
            recyclerView.adapter = adapter
        }

        updateAdapter()

        fabLastOpened.setOnClickListener {
            val last = prefs.getInt("last_surah_index", -1)
            if (last != -1) {
                showSurahDetails(last, surahNames[last])
            }
        }

        switchFav.setOnCheckedChangeListener { _, isChecked ->
            showOnlyFavorites = isChecked
            updateAdapter()
        }
    }

    private fun showSurahDetails(position: Int, surahName: String) {
        val intent = Intent(requireContext(), SurahDetailsActivity::class.java)
        intent.putExtra(Constant.EXTRA_SURAH_POSITION, position)
        intent.putExtra(Constant.EXTRA_SURAH_NAME, surahName)
        startActivity(intent)
    }
}
