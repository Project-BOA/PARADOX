package team.boa.paradox.ui.leaderboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.databinding.FragmentLeaderboardBinding
import team.boa.paradox.network.ApiClient
import team.boa.paradox.network.Room
import team.boa.paradox.network.RoomLeaderboardResponse
import team.boa.paradox.viewmodel.ToolViewModel

class LeaderboardFragment : Fragment() {

    private val toolViewModel: ToolViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

        val client = ApiClient.roomAPIService.leaderboard(Room("alan", "TESTI", null));

        client.enqueue( object : Callback<RoomLeaderboardResponse> {
            override fun onResponse(
                call: Call<RoomLeaderboardResponse>,
                response: Response<RoomLeaderboardResponse>
            ) {
                Log.i("Leaderboard", response.body().toString())
            }

            override fun onFailure(call: Call<RoomLeaderboardResponse>, t: Throwable) {
                Log.i("Leaderboard Failure", t.message.toString())
            }

        })

    }
}