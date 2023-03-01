package team.boa.paradox.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class JoinRoom (
    @Json(name = "username")
    val username: String,

    @Json(name = "roomID")
    val roomID: String
)

data class SubmitRoom (
    @Json(name = "username")
    val username: String,

    @Json(name = "answer")
    val answer: String,

    @Json(name = "roomID")
    val roomID: String
)


data class JoinRoomResponse (
    @Json(name = "status")
    val status: String,

    @Json(name = "score")
    val score: Int
)


data class SubmitRoomRoomResponse (
    @Json(name = "status")
    val status: String,

    @Json(name = "score")
    val score: Int,

    @Json(name = "puzzleID")
    val puzzleID: String
)

interface SubmitRoomAPIService {
    @Headers("Content-type: application/json")
    @POST("room/submit")
    fun submitRoom(@Body room: SubmitRoom): Call<SubmitRoomRoomResponse>
}

interface JoinRoomAPIService {
    @Headers("Content-type: application/json")
    @POST("room/join")
    fun joinRoom(@Body room: JoinRoom): Call<JoinRoomResponse>
}