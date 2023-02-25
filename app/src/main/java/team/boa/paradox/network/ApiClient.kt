package com.example.prototype1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import team.boa.paradox.network.profile.LoginAPIService
import team.boa.paradox.network.profile.RegisterAPIService

object ApiClient{

    // private val BASE_URL = "https://backend-prototype.vercel.app/api/"
    // testing on dev branch
    private val BASE_URL = "https://backend-prototype-git-dev-project-boa.vercel.app/api/"


    //variable for moshi builder to add converter
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    }

    // Profile Api
    val loginApiService: LoginAPIService by lazy{
        retrofit.create(LoginAPIService::class.java)
    }

    val registerAPIService: RegisterAPIService by lazy{
        retrofit.create(RegisterAPIService::class.java)
    }
}