package com.abdulrahman.islami

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.HadethContentAdapter
import com.google.android.material.appbar.MaterialToolbar

class HadethDetailsActivity : AppCompatActivity() {

    private lateinit var adapter: HadethContentAdapter
    private lateinit var hadethRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadeth_details)

        val title = intent.getStringExtra(Constant.EXTRA_HADETH_TITLE)
        val content = intent.getStringExtra(Constant.EXTRA_HADETH_CONTENT)

        // تعيين العنوان في التولبار
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hadethRecyclerView = findViewById(R.id.hadeth_recycler_view)
        adapter = HadethContentAdapter(emptyList())
        hadethRecyclerView.layoutManager = LinearLayoutManager(this)
        hadethRecyclerView.adapter = adapter

        // تقسيم المحتوى لسطر بسطر
        val lines = content?.lines()?.filter { it.isNotBlank() } ?: emptyList()
        adapter.updateContent(lines)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
