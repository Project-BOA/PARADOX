package team.boa.paradox.viewmodel

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
     * Method that clears the viewmodel of login data
     *
     */
    fun logout() {
        _isLoggedIn.value = false
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

}