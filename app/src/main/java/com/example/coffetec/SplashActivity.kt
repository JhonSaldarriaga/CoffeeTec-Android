package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startTimer()
    }
    fun startTimer(){
        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, LoginActivity :: class.java).apply{}
                startActivity(intent)
                finish()
            }
        }.start()
    }
}