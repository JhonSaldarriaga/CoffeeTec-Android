package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffetec.databinding.ActivityShowHarvestBinding

class ShowHarvestActivity : AppCompatActivity() {
    private val binding : ActivityShowHarvestBinding by lazy {
        ActivityShowHarvestBinding.inflate(layoutInflater)
    }

    var harvest:Harvest?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        harvest = intent.extras?.get("harvest") as Harvest

        binding.idHarvest.text = harvest?.id.toString()
        binding.dateHarvest.text = harvest?.date.toString()
        //binding.stateSpinner2.textAlignment = harvest?.state
        binding.numBult.text = harvest?.numBag.toString()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this,EditHarvestActivity::class.java).apply {
                putExtra("harvest",harvest)
            }
            startActivity(intent)
            finish()
        }
    }
}