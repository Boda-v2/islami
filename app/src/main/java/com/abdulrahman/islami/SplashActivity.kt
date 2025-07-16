package com.abdulrahman.islami

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.abdulrahman.islami.Home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper())
            .postDelayed({
                startHomeScreen()
            },500)
    }
    fun startHomeScreen(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}