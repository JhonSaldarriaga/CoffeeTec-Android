package com.example.coffetec

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coffetec.fragments.HarvestFragment

class HarvestViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    var harvest: Harvest?=null
    lateinit var onClickHarvestListener: HarvestFragment

    val id : TextView = itemView.findViewById(R.id.idHarvestRow)
    val numLump : TextView = itemView.findViewById(R.id.numBultRow)
    val state : TextView = itemView.findViewById(R.id.stateHarvestRow)
    val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.constraintLayoutRow)

    init {
        constraintLayout.setOnClickListener {
            var id = harvest!!.id
            Log.d(">>>>>>>>>>>>>>>>>>>",id.toString())
            onClickHarvestListener.openInfoHarvest(id)
        }
    }

    fun bindHarves(harvestBind:Harvest){
        harvest = harvestBind
        id.setText(harvestBind.id)
        numLump.setText(""+harvestBind.numLump)
        state.setText(harvestBind.state)
    }
}