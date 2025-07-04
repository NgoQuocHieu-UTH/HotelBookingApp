package com.example.myapplication.model

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val token: String
)
