package com.example.coffetec

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HarvestViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    var id : TextView = itemView.findViewById(R.id.id_harvest_row)
    var numBag : TextView = itemView.findViewById(R.id.num_bag_row)
    var state : TextView = itemView.findViewById(R.id.state_row)
}