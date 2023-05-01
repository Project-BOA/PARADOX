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
    var username: String,

    @Json(name = "password")
    var password: String,

    @Json(name = "email")
    var email: String?,

    @Json(name = "biography")
    var biography: String?,

    @Json(name = "newPassword")
    val newPassword: String?
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

    @Json(name = "email")
    val email: String?,

    @Json(name = "biography")
    val biography: String?,

    @Json(name = "newPassword")
    val newPassword: String?
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

    // where to send the edit request
    @Headers("Content-type: application/json")
    @POST("profile/edit")
    fun edit(@Body editProfile: Profile): Call<ProfileResponse>

    // where to send the logout request
    @Headers("Content-type: application/json")
    @POST("profile/logout")
    fun logout(@Body logoutProfile: Profile): Call<ProfileResponse>


}
