package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffetec.databinding.ActivityInfoSensorBinding
import com.example.coffetec.databinding.ActivityRegistrationSensorBinding

class InfoSensorActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoSensorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.backAuxSensorHeader.setOnClickListener {
           finish()
       }
    }
}