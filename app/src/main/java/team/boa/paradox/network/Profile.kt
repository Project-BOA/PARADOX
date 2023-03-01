package team.boa.paradox.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Profile data class to hold request
 *
 * @property username the users username
 * @property password the users password
 * @property biography the users biography NOTE: Only necessary on registering
 */
data class Profile (

    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "biography")
    val biography: String?
)

/**
 * Profile response data class to hold repsonse
 *
 * @property status Provides the status of the response
 * @property biography Only exists with login response
 */
data class ProfileResponse (

    @Json(name = "status")
    val status: String,

    @Json(name = "biography")
    val biography: String?
)

/**
 * Profile Application Programming Interface Service
 *
 * Interface defining the functions for interacting with the profile API
 */
interface ProfileAPIService {
    // where to send the login request
    @Headers("Content-type: application/json")
    @POST("profile/login")
    fun login(@Body loginProfile: Profile): Call<ProfileResponse>

    // where to send the register request
    @Headers("Content-type: application/json")
    @POST("profile/register")
    fun register(@Body registerProfile: Profile): Call<ProfileResponse>
}
