package com.example.myapplication.remote

import com.example.myapplication.model.RegisterRequest
import com.example.myapplication.model.RegisterResponse
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}
