package team.boa.paradox.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * Room data class to hold request
 *
 * @constructor Create empty Room
 * @property username the users username
 * @property roomID the room ID the user is in
 * @property answer the answer the user wants to submit NOTE: Only necessary when submitting
 */
data class Room (
    @Json(name = "username")
    val username: String,

    @Json(name = "roomID")
    val roomID: String,

    @Json(name = "answer")
    val answer: String?
)

/**
 * Room data class to hold response
 *
 * @constructor Create empty Room response
 * @property status Provides the status of the response
 * @property score the users score
 * @property puzzleID the puzzle id associated with the room
 */
data class RoomResponse (
    @Json(name = "status")
    val status: String,

    @Json(name = "score")
    val score: Int?,

    @Json(name = "puzzleID")
    val puzzleID: String?
)

/**
 * Leaderboard Profile to hold the data for each leaderboard entry
 *
 * @property name the name of the user
 * @property position the position of the user
 */
data class LeaderboardEntry (
    @Json(name = "position")
    val position: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "score")
    val score: String,
)

/**
 * Room data class to hold the room leaderboard response
 *
 * @constructor Create empty Room response
 * @property status Provides the status of the response
 * @property score the users score
 * @property puzzleID the puzzle id associated with the room
 */
data class RoomLeaderboardResponse (
    @Json(name = "status")
    val status: String,

    @Json(name = "leaderboard")
    val leaderboard: List<LeaderboardEntry>
)


/**
 * Room Application Programming Interface Service
 *
 * Interface defining functions used for interacting with the Room API
 */
interface RoomAPIService {
    @Headers("Content-type: application/json")
    @POST("room/join")
    fun join(@Body room: Room): Call<RoomResponse>

    @Headers("Content-type: application/json")
    @POST("room/submit")
    fun submit(@Body room: Room): Call<RoomResponse>

    @Headers("Content-type: application/json")
    @POST("room/leaderboard")
    fun leaderboard(@Body room: Room): Call<RoomLeaderboardResponse>
}
