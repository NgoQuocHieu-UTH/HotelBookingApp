package com.example.myapplication.model

data class BookingInfo(
    val booking_id: Int,
    val room_type: String,
    val image_url: String,
    val check_in: String,
    val check_out: String,
    val total_price: Double,
    val booking_status: String
)
