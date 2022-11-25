package com.example.coffetec

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HarvestViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    var harvest: Harvest?=null

    var id : TextView = itemView.findViewById(R.id.id_harvest_row)
    var numBag : TextView = itemView.findViewById(R.id.num_bag_row)
    var state : TextView = itemView.findViewById(R.id.state_row)
    var btnEdit: Button = itemView.findViewById(R.id.btnEditrow)

    init {
        btnEdit.setOnClickListener {
            val intent = Intent(itemView.context, ShowHarvestActivity::class.java).apply {
                putExtra("harvest",harvest!!)
            }
            itemView.context.startActivity(intent)
        }
    }
}