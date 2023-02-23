//package com.example.prototype1.network
//
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.http.GET
//
//class ProfileAPIService {
//
//    private val BASE_URL =
//        "https://backend-prototype.vercel.app"
//
//    private val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .baseUrl(BASE_URL)
//        .build()
//
//    interface ProfileAPIService {
//        @GET("profile/login")
//        suspend fun getLogin(): Profile
//    }
//
//    object ProfileAPI {
//        val retrofitService : ProfileAPIService by lazy {
//            retrofit.create(ProfileAPIService::class.java)
//        }
//    }
//}
//
