//package com.example.prototype1.network
//
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import com.example.prototype1.network.RetrofitClient
//import kotlin.jvm.Synchronized
//
//class RetrofitClient private constructor() {
//    val myApi: Api
//
//    init {
//        val retrofit = Retrofit.Builder().baseUrl(Api.BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//        myApi = retrofit.create(Api::class.java)
//    }
//
//    companion object {
//        @get:Synchronized
//        var instance: RetrofitClient? = null
//            get() {
//                if (field == null) {
//                    field = RetrofitClient()
//                }
//                return field
//            }
//            private set
//    }
//}