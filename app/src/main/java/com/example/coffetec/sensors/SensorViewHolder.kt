package com.example.coffetec.sensors

import android.content.Intent
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
        checkStates(sensorBind.state)

    }

    private fun checkStates(state:String) {
        if(state=="inactive"){
            sensorState.setText("Inactivo")
        }else{
            sensorState.setText("Activo")
        }
    }
}