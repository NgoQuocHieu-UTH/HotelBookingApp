package com.example.myapplication.model

data class Room(
    val room_id: Int,
    val hotel_id: Int,
    val room_type: String,
    val area: Int,
    val bed_type: String,
    val capacity: Int,
    val description: String,
    val price_per_night: Double,
    val image_url: String
)

