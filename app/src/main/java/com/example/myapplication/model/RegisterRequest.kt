package com.example.myapplication.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)