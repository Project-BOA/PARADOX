package team.boa.paradox.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import team.boa.paradox.network.Profile
import team.boa.paradox.network.Room

class ProfileViewModel : ViewModel() {

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val roomAdapter: JsonAdapter<Room> by lazy {
        moshi.adapter(Room::class.java)
    }

    private val profileAdapter: JsonAdapter<Profile> by lazy {
        moshi.adapter(Profile::class.java)
    }

    // read and write
    private var _profile = MutableLiveData<Profile>()
    private var _isLoggedIn = MutableLiveData(false)

    // read only
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    /**
     * Load data from viewmodel
     *
     * @param context the activity context to contains the sharedpreferences
     */
    fun loadData(context: Context) {
        val profileSharedPreferences = context.getSharedPreferences("Profile", Context.MODE_PRIVATE)
        profileSharedPreferences.getString("Profile", null)?.let {
            profileAdapter.fromJson(it)?.let { profile -> login(context, profile) }
        }
    }

    /**
     * Method to set the viewmodel login data and then saves it
     *
     * @param username
     * @param biography
     */
    fun login(context: Context, profile: Profile) {
        _profile.value = profile
        _isLoggedIn.value = true

        // save login to preferences
        val profileSharedPreferences = context.getSharedPreferences("Profile", Context.MODE_PRIVATE)
        profileSharedPreferences.edit()
            .putString("Profile", profileAdapter.toJson(getProfile())).apply()
    }

    /**
     * Get the profile of the logged in user
     *
     * @return null if the user is not logged in
     */
    fun getProfile(): Profile? {
        if (isLoggedIn.value == true) {
            return _profile.value
        }
        return null
    }

    /**
     * Method that clears the viewmodel of login data
     *
     */
    fun logout(context: Context) {
        // remove profile from preferences
        val profileSharedPreferences = context.getSharedPreferences("Profile", Context.MODE_PRIVATE)
        profileSharedPreferences.edit().remove("Profile").apply();
        _isLoggedIn.value = false
    }
}