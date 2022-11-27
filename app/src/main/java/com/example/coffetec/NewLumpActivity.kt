package com.example.coffetec

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffetec.databinding.ActivityNewLumpBinding
import com.example.coffetec.fragments.HarvestFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
///import com.google.zxing.BarcodeFormat
///import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*

class NewLumpActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewLumpBinding
    lateinit var harvestId :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLumpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        harvestId = intent.extras?.getString("harvestId", "").toString()

        binding.btnCreate.setOnClickListener {
            var id: String = UUID.randomUUID().toString()
            var weight: String = binding.ETKg.text.toString()
            var qr :String = binding.ivCodigoQR.toString()

            if(weight!="" && qr!=""){
                var lump = Lump(id,weight,qr)

                Firebase.firestore.collection("harvests").document(harvestId)
                    .collection("lumps").document(id).set(lump).addOnCompleteListener {

                        Firebase.firestore.collection("harvests").document(harvestId).get().addOnCompleteListener { harvest ->
                                var harvest = harvest.result.toObject(Harvest::class.java)
                                var numLump =  harvest!!.numLump+1;

                                Firebase.firestore.collection("harvests").document(harvestId)
                                    .update("numLump", numLump).addOnCompleteListener {
                                        val intent = Intent(this, ShowHarvestActivity::class.java).apply {
                                            putExtra("resetHarvest","true")
                                            putExtra("idHarvest",harvestId)
                                        }
                                        Toast.makeText(this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show()
                                        finish()
                                        startActivity(intent)
                                    }
                            }
                    }
            }else {
                Toast.makeText(this, "Por favor, rellenar correctamente los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.ivCodigoQR.setImageBitmap(null)
            binding.etDatos.text.clear()
            finish()
        }

        /**
        binding.btnGenerar.setOnClickListener {
            try{
                var barcodeEncode : BarcodeEncoder = BarcodeEncoder()
                var bitmap : Bitmap = barcodeEncode.encodeBitmap(
                    binding.etDatos.text.toString(),
                    BarcodeFormat.QR_CODE,
                    250,
                    250
                )
                binding.ivCodigoQR.setImageBitmap(bitmap)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
        */
    }
}