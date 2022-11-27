package com.example.coffetec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun showFragment (fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.homeFragment.id, fragment)
        transaction.commit()
    }
}