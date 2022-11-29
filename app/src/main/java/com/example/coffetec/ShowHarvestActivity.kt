package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.coffetec.databinding.ActivityShowHarvestBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

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
            Firebase.firestore.collection("harvests").document(harvestId)
                .update("state",binding.stateSpinner2.selectedItem.toString())
                .addOnCompleteListener {
                    Toast.makeText(this, "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show()
                }
            if(binding.stateSpinner2.selectedItem.toString()=="Empaquetado"){
                binding.btnAddLump.visibility= View.VISIBLE
            }else{
                binding.btnAddLump.visibility= View.GONE
            }
            val intent = Intent(this, HomeActivity::class.java).apply{
                putExtra("harvestReload", "true")
            }
            startActivity(intent)
        }

        binding.btnAddLump.setOnClickListener {
            val intent = Intent(this, NewLumpActivity::class.java).apply {
                putExtra("harvestId",harvestId)
                putExtra("harvest", Gson().toJson(harvest))
            }
            finish()
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadInfo(){
        disableTF()
        Firebase.firestore.collection("harvests")
            .document(harvestId).get().addOnCompleteListener { harvest->
                val harvestInfo: Harvest = harvest.result.toObject(Harvest::class.java)!!
                if(harvestInfo.id!=""){
                    binding.idHarvest.setText("ID: "+harvestInfo.id.substring(0,10))
                    binding.dateHarvest.setText("Fecha: "+harvestInfo.date)
                    binding.numBult.setText("Numero de bultos "+harvestInfo.numLump.toString())
                    binding.stateSpinner2.setSelection(verificateStateIndex(harvestInfo.state))
                }

                if(harvestInfo.state=="Empaquetado"){
                    binding.btnAddLump.visibility= View.VISIBLE
                }else{
                    binding.btnAddLump.visibility= View.GONE
                }
            }
    }

    private fun disableTF(){
        binding.idHarvest.setOnKeyListener(null)
        binding.numBult.setOnKeyListener(null)
        binding.dateHarvest.setOnKeyListener(null)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun verificateStateIndex(state:String): Int{
        val value:Int
        if (state == "Clasificado"){
            value=0
        }else if(state == "Despulpado"){
            value=1
        }else if(state == "Lavdo y Fermentación"){
            value=2
        }else if(state == "Secado"){
            value=3
        }else{
            value=4
        }
        return value
    }
}