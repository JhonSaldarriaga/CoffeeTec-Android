package com.example.coffetec

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HarvestAdapter: RecyclerView.Adapter<HarvestViewHolder>() {

    private val harvests = ArrayList<Harvest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarvestViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.harvestrow, parent, false)
        val harvestView = HarvestViewHolder(rowView)
        return harvestView
    }

    override fun onBindViewHolder(holder: HarvestViewHolder, position: Int) {
        val harvestn = harvests[position]
        holder.id.text = harvestn.id
        holder.numBag.text = ""+harvestn.numBag
        holder.state.text = harvestn.state
    }

    fun addHarvest(harvest:Harvest){
        harvests.add(harvest)
    }

    override fun getItemCount(): Int {
        return  harvests.size
    }

}