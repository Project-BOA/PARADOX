package team.boa.paradox.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient{

    // production branch
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
    val loginApiService: LoginProfileAPIService by lazy {
        retrofit.create(LoginProfileAPIService::class.java)
    }

    val registerProfileAPIService: RegisterProfileAPIService by lazy {
        retrofit.create(RegisterProfileAPIService::class.java)
    }

    val joinRoomAPIService: JoinRoomAPIService by lazy {
        retrofit.create(JoinRoomAPIService::class.java)
    }
}