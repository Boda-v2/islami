package com.abdulrahman.islami.Home

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abdulrahman.islami.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager = findViewById(R.id.view_pager)
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)

        val adapter = HomePagerAdapter(this)
        viewPager.adapter = adapter

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_quran -> viewPager.currentItem = 0
                R.id.navigation_hadeth -> viewPager.currentItem = 1
                R.id.navigation_sebha -> viewPager.currentItem = 2
                R.id.navigation_azkar -> viewPager.currentItem = 3
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val itemId = when (position) {
                    0 -> R.id.navigation_quran
                    1 -> R.id.navigation_hadeth
                    2 -> R.id.navigation_sebha
                    3 -> R.id.navigation_azkar
                    else -> R.id.navigation_quran
                }
                bottomNavigationView.selectedItemId = itemId
            }
        })

        bottomNavigationView.selectedItemId = R.id.navigation_quran
    }

    private fun setViewPagerScrollSpeed(viewPager: ViewPager2, speedFactor: Float) {
        try {
            val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true
            val recyclerView = recyclerViewField.get(viewPager) as RecyclerView

            val layoutManager = object : LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) {
                override fun smoothScrollToPosition(
                    recyclerView: RecyclerView,
                    state: RecyclerView.State,
                    position: Int
                ) {
                    val smoothScroller = object : LinearSmoothScroller(this@HomeActivity) {
                        override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                            return (100f / displayMetrics.densityDpi) * speedFactor
                        }
                    }
                    smoothScroller.targetPosition = position
                    startSmoothScroll(smoothScroller)
                }
            }

            recyclerView.layoutManager = layoutManager
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
