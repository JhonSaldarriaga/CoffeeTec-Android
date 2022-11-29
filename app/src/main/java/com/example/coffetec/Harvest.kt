package com.example.coffetec

import java.io.Serializable
import java.util.UUID

data class Harvest(val id:String="",
                   val numLump: Int=0,
                   val date: String="",
                   val state: String="") : Serializable
