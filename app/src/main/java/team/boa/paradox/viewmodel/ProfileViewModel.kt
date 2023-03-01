package team.boa.paradox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    // read and write
    private var _username = MutableLiveData<String>()
    private var _email = MutableLiveData<String>()
    private var _biography = MutableLiveData<String>()
    private var _isLoggedIn = MutableLiveData(false)

    // read only
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn
    val username: LiveData<String> = _username
    val email: LiveData<String> = _email
    val biography: LiveData<String> = _biography

    /**
     * Method to set the viewmodel login data
     *
     * @param username
     * @param biography
     */
    fun setUserLoggedIn(username: String, email: String, biography: String) {
        _username.value = username
        _email.value = email
        _biography.value = biography
        _isLoggedIn.value = true
    }

    /**
     * Method that clears the viewmodel of login data
     *
     */
    fun setUserloggedOut() {
        _isLoggedIn.value = false
        _username.value = ""
        _email.value = ""
        _biography.value = ""
    }

}