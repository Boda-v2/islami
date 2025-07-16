package com.abdulrahman.islami.Home.fragment

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.VersesAdapter
import com.abdulrahman.islami.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SurahDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VersesAdapter
    private lateinit var fabBookmark: FloatingActionButton

    private var surahPosition: Int = -1
    private lateinit var surahName: String
    private lateinit var verses: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah_details)

        // استقبال البيانات
        surahPosition = intent.getIntExtra(Constant.EXTRA_SURAH_POSITION, -1)
        surahName = intent.getStringExtra(Constant.EXTRA_SURAH_NAME) ?: ""

        // تحميل الآيات
        verses = readSurahFile(surahPosition)

        // إعداد الواجهة
        val titleTextView = findViewById<TextView>(R.id.title_text_view)
        recyclerView = findViewById(R.id.verses_recycler_view)
        fabBookmark = findViewById(R.id.fab_bookmark)

        titleTextView.text = surahName
        adapter = VersesAdapter()

        // تمرير bookmark الحالي
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val currentBookmark = prefs.getInt("bookmark_$surahPosition", -1)
        adapter.bookmarkPosition = currentBookmark

        adapter.onLongClick = { index ->
            handleBookmarkToggle(index)
        }

        adapter.changeData(verses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Scroll إلى bookmark
        if (currentBookmark != -1) {
            fabBookmark.show()
            fabBookmark.setOnClickListener {
                recyclerView.scrollToPosition(currentBookmark)
            }
        } else {
            fabBookmark.hide()
        }

        // Scroll إلى آخر آية
        val lastAyahIndex = prefs.getInt("last_ayah_$surahPosition", 0)
        recyclerView.scrollToPosition(lastAyahIndex)
    }

    private fun handleBookmarkToggle(index: Int) {
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        val current = prefs.getInt("bookmark_$surahPosition", -1)

        if (current == index) {
            // حذف العلامة
            editor.remove("bookmark_$surahPosition").apply()
            adapter.bookmarkPosition = -1
            Toast.makeText(this, "تم حذف العلامة المرجعية", Toast.LENGTH_SHORT).show()
            fabBookmark.hide()
        } else {
            // حفظ جديدة
            editor.putInt("bookmark_$surahPosition", index).apply()
            adapter.bookmarkPosition = index
            Toast.makeText(this, "تم تعيين العلامة المرجعية عند الآية رقم ${index + 1}", Toast.LENGTH_SHORT).show()
            fabBookmark.show()
        }

        adapter.notifyDataSetChanged()
    }

    private fun readSurahFile(position: Int): List<String> {
        val fileName = "quran/${position + 1}.txt"
        val inputStream = assets.open(fileName)
        return inputStream.bufferedReader().readLines()
    }

    override fun onPause() {
        super.onPause()
        val visible = (recyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()

        getSharedPreferences("prefs", MODE_PRIVATE)
            .edit()
            .putInt("last_ayah_$surahPosition", visible)
            .apply()
    }
}
