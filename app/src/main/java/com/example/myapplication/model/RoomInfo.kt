package com.example.myapplication.model


data class RoomInfo(
    val id: Int,
    val room_type: String,
    val image_url: String,
    val area: Int,
    val bed_type: String,
    val capacity: Int,
    val description: String,
    val price: Double
)
