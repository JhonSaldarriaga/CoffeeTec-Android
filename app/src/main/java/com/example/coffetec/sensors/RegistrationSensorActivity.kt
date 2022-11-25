package com.example.coffetec.sensors

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffetec.HomeActivity
import com.example.coffetec.databinding.ActivityRegistrationSensorBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*

class RegistrationSensorActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationSensorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registSensorButton.setOnClickListener {
            var name = binding.nameSensorRegistText.text.toString()
            var id = binding.idSensorRegistrationTextField.text.toString()

            //// Conectar spinner
            if (name != "" && id != "") {
                var sensor = Sensor(id, name, "", "", "Active")
                Firebase.firestore.collection("sensors")
                    .document(id)
                    .set(sensor).addOnCompleteListener {
                        Toast.makeText(this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show()
                    }

                val intent = Intent(this,HomeActivity::class.java).apply{
                    putExtra("newSensor", "true")
                    putExtra("sensor", Gson().toJson(sensor))
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, rellenar correctamente los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backAuxSensorHeader.setOnClickListener {
            finish()
        }
    }
}