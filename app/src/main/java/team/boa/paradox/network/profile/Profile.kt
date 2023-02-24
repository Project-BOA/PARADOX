package team.boa.paradox.network.profile

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


data class Profile (

    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String
)


data class ProfileResponse (

    @Json(name = "status")
    val status: String,

    @Json(name = "biography")
    val biography: String?
)


interface LoginAPIService {
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