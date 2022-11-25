package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffetec.databinding.ActivityRegistrationSensorBinding
import com.example.coffetec.databinding.FragmentSensorsDashboardBinding

class RegistrationSensorActivity : AppCompatActivity() {


    lateinit var binding: ActivityRegistrationSensorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancellRegistrationSensorButton.setOnClickListener {
            finish()
        }

        binding.registSensorButton.setOnClickListener {
            val intent = Intent(this, InfoSensorActivity::class.java)
            startActivity(intent)
        }

        binding.backAuxSensorHeader.setOnClickListener {
            finish()
        }
    }
}