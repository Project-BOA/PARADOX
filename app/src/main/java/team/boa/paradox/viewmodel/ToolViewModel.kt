package team.boa.paradox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import team.boa.paradox.network.Room

class ToolViewModel : ViewModel() {
    // read and write
    private var _isInRoom = MutableLiveData(false)
    private var _room =  MutableLiveData<Room>()

    // read only
    val isInRoom: LiveData<Boolean> = _isInRoom
    val room: LiveData<Room> = _room

    fun setRoom(room : Room) {
        _room.value = room
        _isInRoom.value = true
    }
}