package com.example.myapplication.remote

import com.example.myapplication.model.*
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/rooms/search")
    suspend fun searchRooms(@Body request: SearchRequest): Response<RoomResponse>

    @POST("/api/searchHotels")
    suspend fun searchRoomsByHotel(@Body search: SearchInfo): Response<List<Room>>

    @POST("/api/user/update/user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Response<ApiResponse>

    @POST("/api/book-room")
    suspend fun bookRoom(
        @Header("Authorization") token: String,
        @Body request: BookingRequest
    ): Response<ApiResponse>

    @GET("/api/user/bookings")
    suspend fun getMyBookings(
        @Header("Authorization") token: String
    ): Response<List<BookingInfo>>

    @POST("/api/bookings/cancel")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Body bookingId: Map<String, Int>
    ): Response<ApiResponse>

    @GET("/api/rooms/available")
    suspend fun getAvailableRooms(): Response<List<RoomInfo>>


}
