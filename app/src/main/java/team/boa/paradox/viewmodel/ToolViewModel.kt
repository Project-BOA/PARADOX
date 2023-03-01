package team.boa.paradox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolViewModel : ViewModel() {
    // read and write
    private var _isInRoom = MutableLiveData(false)
    private var _roomID = MutableLiveData<String>()
    private var _score =  MutableLiveData<Int>()

    // read only
    val isInRoom: LiveData<Boolean> = _isInRoom
    val roomId: LiveData<String> = _roomID
    val score: LiveData<Int> = _score

    fun addUserToRoom(roomId: String, score: Int) {
        _isInRoom.value = true
        _roomID.value = roomId
        _score.value = score
    }
}