package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffetec.databinding.ActivityShowHarvestBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ShowHarvestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShowHarvestBinding
    lateinit var harvestId: String
    var harvest:Harvest?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowHarvestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        harvestId = intent.extras?.getString("idHarvest","").toString()
        loadInfo()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this,EditHarvestActivity::class.java).apply {
                putExtra("harvest",harvest)
            }
            startActivity(intent)
            finish()
        }

    }

    private fun loadInfo(){
        Firebase.firestore.collection("harvests")
            .document(harvestId).get().addOnCompleteListener { harvest->
                var harvestInfo: Harvest = harvest.result.toObject(Harvest::class.java)!!
                if(harvestInfo.id!=""){
                    binding.idHarvest.setText(harvestInfo.id)
                    binding.dateHarvest.setText(harvestInfo.date)
                    binding.numBult.setText(harvestInfo.numBag)
                }
            }
    }
}