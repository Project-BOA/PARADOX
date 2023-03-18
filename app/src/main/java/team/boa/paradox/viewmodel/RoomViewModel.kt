package team.boa.paradox.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import team.boa.paradox.network.Room

class RoomViewModel : ViewModel() {
    // read and write
    private var _isInRoom = MutableLiveData(false)
    private var _room =  MutableLiveData<Room>()

    // read only
    val isInRoom: LiveData<Boolean> = _isInRoom

    fun setRoom(room : Room) {
        _room.value = room
        _isInRoom.value = true
    }

    fun getRoom(): Room? {
        if (isInRoom.value == true) {
            return _room.value
        }
        return null
    }

    fun leave(context: Context) {
        val roomSharedPreferences = context.getSharedPreferences("Room", Context.MODE_PRIVATE)
        // remove notes and room from preferences
        roomSharedPreferences.edit().remove("Room").apply();
        roomSharedPreferences.edit().remove("Notes").apply();
        _isInRoom.value = false
    }
}