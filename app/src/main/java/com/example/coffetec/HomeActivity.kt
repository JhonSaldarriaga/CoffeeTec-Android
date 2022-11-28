package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.coffetec.databinding.ActivityHomeBinding
import com.example.coffetec.fragments.HarvestFragment
import com.example.coffetec.sensors.SensorsDashboardFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var harvestFragment: HarvestFragment
    private lateinit var sensorsFragment: SensorsDashboardFragment
    private lateinit var newHarvest: NewHarvest
    private val binding : ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var userId = intent.extras?.getString("userId", "").toString()

        Log.e(" 26 - HomeActivity ", userId)

        harvestFragment = HarvestFragment.newInstance()
        sensorsFragment = SensorsDashboardFragment.newInstance()

        //suscription
        binding.navigator.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.harvestmenu -> {showFragment(harvestFragment)}
                R.id.sensoresmenu -> {showFragment(sensorsFragment)}
                R.id.harvestmenu -> {showFragment(harvestFragment)}
            }
            true
        }

        binding.profileButton.setOnClickListener{
            val intent = Intent(this, ProfileActivity :: class.java).apply {
                putExtra("userId", userId)
            }
            startActivity(intent)
        }
    }

    private fun showFragment (fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.homeFragment.id, fragment)
        transaction.commit()
    }
}