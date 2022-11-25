package com.example.coffetec

import java.io.Serializable
import java.util.UUID

data class Harvest(val id:String="",
                   val numBag: Int=0,
                   val date: String="",
                   val qr: String="",
                   val state: String="") : Serializable
