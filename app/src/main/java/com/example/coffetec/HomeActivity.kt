package com.example.coffetec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coffetec.databinding.ActivityHomeBinding
import com.example.coffetec.fragments.HarvestFragment
import com.example.coffetec.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var harvestFragment: HarvestFragment
    private lateinit var profileFragment : ProfileFragment
    private val binding : ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        harvestFragment = HarvestFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()

        binding.navigator.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.harvestmenu -> {showFragment(harvestFragment)}
                R.id.homemenu -> {showFragment(profileFragment)}
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