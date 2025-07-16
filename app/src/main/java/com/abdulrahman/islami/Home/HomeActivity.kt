package com.abdulrahman.islami.Home

import androidx.appcompat.app.AppCompatActivity
import com.abdulrahman.islami.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.abdulrahman.islami.Home.fragment.HadethFragment
import com.abdulrahman.islami.Home.fragment.QuranFragment
import com.abdulrahman.islami.Home.fragment.AzkarFragment
import com.abdulrahman.islami.Home.fragment.SebhaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)

        bottomNavigationView.setOnItemSelectedListener {menuItem->
            if (menuItem.itemId == R.id.navigation_quran) {
                pushFragment(QuranFragment())
            } else if (menuItem.itemId == R.id.navigation_sebha) {
                pushFragment(SebhaFragment())
            } else if (menuItem.itemId == R.id.navigation_radio) {
                pushFragment(AzkarFragment())
            } else if (menuItem.itemId == R.id.navigation_hadeth) {
                pushFragment(HadethFragment())
            }
            return@setOnItemSelectedListener true ;
        }

        bottomNavigationView.selectedItemId = R.id.navigation_quran


    }
    fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }

}