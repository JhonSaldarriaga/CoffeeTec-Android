package com.example.coffetec

import java.util.UUID

class Harvest(var numBag: Int, var date: String, var qr: String, var state: String) {
    var id: String = UUID.randomUUID().toString()
}