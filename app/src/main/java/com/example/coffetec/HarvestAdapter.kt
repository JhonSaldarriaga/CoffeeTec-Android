package com.example.coffetec

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffetec.fragments.HarvestFragment
import kotlin.math.log

class HarvestAdapter: RecyclerView.Adapter<HarvestViewHolder>() {

    private val harvests = ArrayList<Harvest>()
    lateinit var onClickHarvestListener: HarvestFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarvestViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.harvest_row, parent, false)
        val harvestView = HarvestViewHolder(rowView)
        harvestView.onClickHarvestListener = onClickHarvestListener
        return harvestView
    }

    override fun onBindViewHolder(holder: HarvestViewHolder, position: Int) {
        val harvestn = harvests[position]
        //holder.bindHarvest(harvestn)
        Log.d(">>>>>>>>>>>>>>>>>>","bind")
        holder.id.text = harvestn.id
        holder.numLump.text = ""+harvestn.numLump
        holder.state.text = harvestn.state
    }

    fun addHarvest(harvest:Harvest){
        harvests.add(harvest)
        notifyItemInserted(harvests.size-1)
    }

    override fun getItemCount(): Int {
        return  harvests.size
    }

    fun clear() {
        val size = harvests.size
        harvests.clear()
        notifyItemRangeRemoved(0,size)
    }

    interface OnClickHarvestListener{
        fun openInfoHarvest(id: String)
    }

}