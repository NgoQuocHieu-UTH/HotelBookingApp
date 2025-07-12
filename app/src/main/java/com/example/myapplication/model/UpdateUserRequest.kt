package com.example.myapplication.model
import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("birth_date") val birth_date: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("identity_number") val identity_number: String? = null
)
