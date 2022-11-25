package com.example.coffetec

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffetec.databinding.ActivityNewHarvestBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.SimpleDateFormat
import java.util.*

class NewHarvest : AppCompatActivity() {

    private lateinit var binding : ActivityNewHarvestBinding


    //Listener

    var listener : OnNewHarvestListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHarvestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCreate.setOnClickListener{
            var id: String = UUID.randomUUID().toString()
            val numBul = 0
            val date = getCurrentDateTime().toString("yyyy/MM/dd")
            val qr = binding.ivCodigoQR.toString()
            val state = binding.stateSpinner.toString()

            listener?.let {
                it.onNewHarvest(id, numBul,date,qr,state)
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.ivCodigoQR.setImageBitmap(null)
            binding.etDatos.text.clear()
        }

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
    }
    interface OnNewHarvestListener{
        fun onNewHarvest(id:String, numBul:Int,date:String,qr:String,state:String)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}