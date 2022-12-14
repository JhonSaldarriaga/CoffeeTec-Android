package com.example.coffetec.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coffetec.R
import com.example.coffetec.model.Tree

class TreesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    lateinit var tree: Tree
    lateinit var listener: Listener
    /// UI Components
    private val rowLayout : ConstraintLayout = itemView.findViewById(R.id.row_layout)
    private val showButton : ImageView = itemView.findViewById(R.id.show_cafeto_row)
    private val showImageButton : ImageView = itemView.findViewById(R.id.show_image_cafeto_row)
    val name : TextView = itemView.findViewById(R.id.name_cafeto_row)
    val location : TextView = itemView.findViewById(R.id.location_cafeto_row)
    val state : TextView = itemView.findViewById(R.id.state_cafeto_row)

    init {
        showButton.setOnClickListener {
            listener.onClickShowTree(tree)
        }
        rowLayout.setOnClickListener {
            listener.onClickShowTree(tree)
        }
        showImageButton.setOnClickListener {
            listener.onClickShowTreePhoto(tree)
        }
    }

    interface Listener{
        fun onClickShowTree(tree: Tree)
        fun onClickShowTreePhoto(tree: Tree)
    }
}