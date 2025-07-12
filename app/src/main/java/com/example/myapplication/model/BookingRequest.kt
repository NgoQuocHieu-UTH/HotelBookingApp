package com.example.myapplication.model


data class BookingRequest(
    val room_id: Int,
    val check_in: String,
    val check_out: String,
    val total_price: Double
)
