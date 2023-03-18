package team.boa.paradox.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import team.boa.paradox.network.Profile

class ProfileViewModel : ViewModel() {
    // read and write
    private var _profile = MutableLiveData<Profile>()
    private var _isLoggedIn = MutableLiveData(false)

    // read only
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    /**
     * Method to set the viewmodel login data
     *
     * @param username
     * @param biography
     */
    fun login(profile: Profile) {
        _profile.value = profile
        _isLoggedIn.value = true
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