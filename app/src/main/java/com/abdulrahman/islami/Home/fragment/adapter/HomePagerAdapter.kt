package com.abdulrahman.islami.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdulrahman.islami.Home.fragment.*

class HomePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QuranFragment()
            1 -> HadethFragment()
            2 -> SebhaFragment()
            3 -> AzkarFragment()
            else -> QuranFragment()
        }
    }
}
