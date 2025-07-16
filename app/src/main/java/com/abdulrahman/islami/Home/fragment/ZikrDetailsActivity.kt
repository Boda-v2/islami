package com.abdulrahman.islami

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.islami.Home.fragment.adapter.ZikrContentAdapter
import com.google.android.material.appbar.MaterialToolbar

class ZikrDetailsActivity : AppCompatActivity() {

    private lateinit var adapter: ZikrContentAdapter
    private lateinit var zikrRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zikr_details)

        val title = intent.getStringExtra(Constant.EXTRA_ZIKR_TITLE)
        val content = intent.getStringExtra(Constant.EXTRA_ZIKR_CONTENT)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        zikrRecyclerView = findViewById(R.id.zikr_detail_recycler)
        adapter = ZikrContentAdapter(emptyList())
        zikrRecyclerView.layoutManager = LinearLayoutManager(this)
        zikrRecyclerView.adapter = adapter

        val lines = content?.lines()?.filter { it.isNotBlank() } ?: emptyList()
        adapter.updateContent(lines)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
