package team.boa.paradox.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


data class LoginProfile (

    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String
)

data class RegisterProfile (

    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "biography")
    val biography: String
)


data class LoginProfileResponse (

    @Json(name = "status")
    val status: String,

    @Json(name = "biography")
    val biography: String?
)

data class RegisterProfileResponse (
    @Json(name = "status")
    val status: String
)


interface LoginProfileAPIService {
    // tell server to get profile
    @Headers("Content-type: application/json")
    @POST("profile/login")
    fun login(@Body loginProfile: LoginProfile): Call<LoginProfileResponse>
}


interface RegisterProfileAPIService {
    // tell server to register profile
    @Headers("Content-type: application/json")
    @POST("profile/register")
    fun register(@Body registerProfile: RegisterProfile): Call<RegisterProfileResponse>
}