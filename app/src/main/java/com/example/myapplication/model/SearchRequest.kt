package com.example.myapplication.model

data class SearchRequest(
    val hotelName: String,
    val checkInDate: String = "",
    val checkOutDate: String = "",
    val checkInTime: String = "",
    val checkOutTime: String = "",
    val numberOfGuests: String = ""
)