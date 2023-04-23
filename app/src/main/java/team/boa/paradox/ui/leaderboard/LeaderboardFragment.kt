package team.boa.paradox.ui.leaderboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.databinding.FragmentLeaderboardBinding
import team.boa.paradox.network.ApiClient
import team.boa.paradox.network.Room
import team.boa.paradox.network.RoomLeaderboardResponse
import team.boa.paradox.viewmodel.RoomViewModel

class LeaderboardFragment : Fragment() {

    private val toolData: RoomViewModel by activityViewModels()
    private lateinit var binding: FragmentLeaderboardBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Leaderboard"
        return binding.root
    }

    private fun displayLeaderboard(room: Room) {
        binding.loadingLeaderboard.isVisible = true

        val client = ApiClient.roomAPIService.leaderboard(room)
        client.enqueue( object : Callback<RoomLeaderboardResponse> {
            override fun onResponse(
                call: Call<RoomLeaderboardResponse>,
                response: Response<RoomLeaderboardResponse>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        Log.i("Leaderboard", response.body().toString())
                        binding.recyclerViewLeaderboard.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                        binding.recyclerViewLeaderboard.adapter = LeaderboardAdapter(response.body()?.leaderboard!!)
                        binding.loadingLeaderboard.isVisible = false
                    } else {
                        binding.textViewMessageLeaderboard.text = "Error loading leaderboard"
                        binding.textViewMessageLeaderboard.isVisible = true
                        binding.loadingLeaderboard.isVisible = false
                    }
                }
            }

            override fun onFailure(call: Call<RoomLeaderboardResponse>, t: Throwable) {
                if (isAdded) {
                    Log.i("Leaderboard Failure", t.message.toString())
                    binding.loadingLeaderboard.isVisible = false
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

        // show if user is in room
        if (toolData.isInRoom.value == true) {
            binding.recyclerViewLeaderboard.isVisible = true
            displayLeaderboard(toolData.getRoom()!!)
        } else {
            binding.textViewMessageLeaderboard.isVisible = true
        }
    }
}