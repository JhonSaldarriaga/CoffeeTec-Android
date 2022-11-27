package com.example.coffetec.sensors

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase


object Util {
    fun initRecycler(recycler: RecyclerView, activity: Activity, orientation: Int) : RecyclerView{
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity, orientation, false)
        return recycler
    }
}