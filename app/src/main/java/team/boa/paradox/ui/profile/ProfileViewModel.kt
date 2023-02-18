package team.boa.paradox.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel() : ViewModel() {
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
     * Authentication method to authenticate with a username and password
     *
     * @param username
     * @param password
     * @return true if successful and false if unsuccessful
     */
    fun authenticate(username: String, password:String): Boolean {

        // authentication logic here

        _username.value = username
        _email.value = ""
        _biography.value = ""
        _isLoggedIn.value = true
        return true
    }

    /**
     * Logout method that clears the viewmodel of login data
     *
     */
    fun logout() {
        _isLoggedIn.value = false
        _username.value = ""
        _email.value = ""
        _biography.value = ""
    }

    /**
     * Register method to register a new user with a username, password and email
     *
     * @param username
     * @param password
     * @param email
     * @return true if successful and false if unsuccessful
     */
    fun register(username: String, password: String, email: String): Boolean {

        // add user to database

        _username.value = ""
        _email.value = ""
        _biography.value = ""

        return true
    }
}