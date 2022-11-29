package com.example.coffetec.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffetec.R
import com.example.coffetec.model.Tree

class TreesAdapter : RecyclerView.Adapter<TreesViewHolder>(){
    lateinit var listener: TreesViewHolder.Listener
    var trees = ArrayList<Tree>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreesViewHolder {
        //Inflater: XML-> View
        val inflater = LayoutInflater.from(parent.context)
        //row = View
        val row = inflater.inflate(R.layout.cafeto_row, parent, false)
        val treeView = TreesViewHolder(row)
        treeView.listener = listener
        return treeView
    }

    override fun onBindViewHolder(holder: TreesViewHolder, position: Int) {
        val actualTree = trees[position]
        holder.tree = actualTree
        holder.name.text = actualTree.name
        holder.location.text = "LAT:${actualTree.latitude}, LON:${actualTree.longitude}"
        holder.state.text = actualTree.state
    }

    override fun getItemCount(): Int {
        return trees.size
    }

    fun addTree(tree:Tree){
        trees.add(tree)
        notifyDataSetChanged()
    }

    fun removeTree(tree: Tree){
        var position = trees.indexOf(tree)
        notifyItemRemoved(position)
        trees.removeAt(position)
    }
}