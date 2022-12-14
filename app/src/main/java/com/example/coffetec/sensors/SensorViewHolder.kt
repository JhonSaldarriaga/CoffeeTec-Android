package com.example.coffetec.sensors

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coffetec.R
import java.io.File


class SensorViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    //STATE
    var sensor: Sensor? = null
    lateinit var onClickSensorListener: SensorsDashboardFragment

    //UI controllers
    var sensorName: TextView = itemView.findViewById(R.id.sensorNameRow)
    var sensorPlace: TextView = itemView.findViewById(R.id.placeSensorRow)
    var sensorCoordinates: TextView = itemView.findViewById(R.id.coordinatesSensorRow)
    var sensorState: TextView = itemView.findViewById(R.id.stateSensorRow)
    var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayoutRow)
    var sensorType: TextView = itemView.findViewById(R.id.typeSensorText)
    var sensorImg: ImageView = itemView.findViewById(R.id.imageStateSensorRow)

    init {
        constraintLayout.setOnClickListener {
            var id = sensor!!.id
            onClickSensorListener.openInfoSensor(id)
        }
    }

    fun bindSensor(sensorBind: Sensor) {
        sensor = sensorBind
        sensorName.setText(sensorBind.name)
        sensorPlace.setText("Ubicación: "+sensorBind.place)
        sensorCoordinates.setText(sensorBind.coordinates)
        sensorType.setText("Tipo: "+sensorBind.type)
        checkStates(sensorBind.state)
    }

    private fun checkStates(state:String) {
        if(state=="inactivo"){
            sensorState.setText("Inactivo")
            sensorState.setTextColor(Color.RED)
            sensorImg.setImageResource(R.drawable.ic_baseline_wifi_off_24)
        }else{
            sensorState.setText("Activo")
            sensorState.setTextColor(Color.rgb(41,112,18))
            sensorImg.setImageResource(R.drawable.ic_baseline_wifi_green_24)

        }
    }
}