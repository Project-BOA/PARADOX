package com.example.prototype1.network

import com.squareup.moshi.Json

data class Profile (

    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String
)

data class ProfileResponse (
    @Json(name = "status")
    val status: String
)