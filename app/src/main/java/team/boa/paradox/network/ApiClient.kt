package com.example.prototype1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object ApiClient{


    private val BASE_URL = "https://backend-prototype.vercel.app/api/"

    //variable for moshi builder to add converter
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    }

    // Profile Api
    val profileApiService: ProfileAPIService by lazy{
        retrofit.create(ProfileAPIService::class.java)
    }

    val registerAPIService: RegisterAPIService by lazy{
        retrofit.create(RegisterAPIService::class.java)
    }
}

interface ProfileAPIService {
        // tell server to get profile
        @Headers("Content-type: application/json")
        @POST("profile/login")
        fun validateLogin(@Body profile: Profile): Call<ProfileResponse>

}


interface RegisterAPIService {
    // tell server to get profile
    @Headers("Content-type: application/json")
    @POST("profile/register")
    fun validateRegister(@Body profile: Profile): Call<ProfileResponse>

}