package com.example.coffetec

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Util {
    fun initRecycler(recycler: RecyclerView, activity: Activity, orientation: Int) : RecyclerView{
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity, orientation, false)
        return recycler
    }

    fun numLump(id:String): Int{
        var numBult = 0
        Firebase.firestore.collection("harvests").document(id).collection("lumps")
            .get().addOnCompleteListener { lumps->
                for(i in lumps.result!!){
                    numBult++
                }
            }
        return numBult
    }
}