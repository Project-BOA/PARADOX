package team.boa.paradox

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import team.boa.paradox.databinding.ActivityMainBinding
import team.boa.paradox.network.Profile
import team.boa.paradox.network.Room
import team.boa.paradox.viewmodel.ProfileViewModel
import team.boa.paradox.viewmodel.RoomViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var toolSharedPreferences: SharedPreferences
    private lateinit var profileSharedPreferences: SharedPreferences
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var profileViewModel: ProfileViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // get viewmodel shared preferences
        roomViewModel = ViewModelProvider(this)[RoomViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        toolSharedPreferences = getSharedPreferences("Tools", Context.MODE_PRIVATE)
        profileSharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE)

        // check saved preferences for data and load into viewmodel

        toolSharedPreferences.getString("Room", null)?.let {
            roomAdapter.fromJson(it)?.let { room -> roomViewModel.setRoom(room) }
        }

        setContentView(binding.root)

        // set background
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.darkGreen));

        // set actionbar background
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.lightGreen)))

        // add navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id != navView.selectedItemId) {
                controller.backQueue.asReversed().drop(1).forEach { entry ->
                    navView.menu.forEach { item ->
                        if (entry.destination.id == item.itemId) {
                            item.isChecked = true
                            return@addOnDestinationChangedListener
                        }
                    }
                }
            }
        }
        navView.setupWithNavController(navController)
    }


    override fun onDestroy() {
        super.onDestroy()

        // add persistent preferences
    }
}
