package com.example.coffetec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coffetec.databinding.ActivityNewHarvestBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import com.google.gson.Gson
import java.util.*

class NewHarvest : AppCompatActivity() {

    private lateinit var binding : ActivityNewHarvestBinding
    private lateinit var lumpId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHarvestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lumpId = intent.extras?.getString("lumpId","").toString()

        binding.btnCreate.setOnClickListener{

            var id: String = UUID.randomUUID().toString()
            val numLump = Util.numLump(id)
            val date = getCurrentDateTime().toString("yyyy/MM/dd")
            //val qr = binding.ivCodigoQR.toString()
            val state = binding.stateSpinner.selectedItem.toString()

            if(id!="" && date!="" && state!="") {
                var harvest = Harvest(id, numLump,date,state)

                Firebase.firestore.collection("harvests")
                    .document(id).set(harvest).addOnCompleteListener {
                        Toast.makeText(this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show()
                    }

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("newHarvest","true")
                    putExtra("harvest", Gson().toJson(harvest))
                }
                startActivity(intent)
            }else {
                Toast.makeText(this, "Por favor, rellenar correctamente los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onBackPressed() {
        finish()
    }
}