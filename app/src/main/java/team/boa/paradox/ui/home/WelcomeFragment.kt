package team.boa.paradox.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentWelcomeBinding
import team.boa.paradox.network.RoomResponse
import team.boa.paradox.network.Room
import team.boa.paradox.viewmodel.ProfileViewModel
import team.boa.paradox.viewmodel.ToolViewModel

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController
    private val toolViewModel: ToolViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Join Room"
        return binding.root
    }

    // clean a string to match puzzle id format
    private fun cleanRoomID(inputString: String): String {
        return inputString
            .uppercase() // convert input to uppercase if not already
            .replace("[^\\dA-Z]".toRegex(), "") // only allow numbers, capital letters and spaces
            .trim() // trim any excess spaces
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        // check user is signed in before showing
        binding.editTextPuzzleId.isVisible = false
        binding.buttonPuzzleId.isVisible = false

        // show if user is logged in
        if (profileViewModel.isLoggedIn.value == true) {
            binding.editTextPuzzleId.isVisible = true
            binding.buttonPuzzleId.isVisible = true
        } else {
            binding.textViewMessage.text = "Please sign in first"
            binding.textViewMessage.isVisible = true
        }

        binding.buttonPuzzleId.setOnClickListener {
            binding.loadingSubmit.isVisible = true // enable loading animation
            binding.buttonPuzzleId.isClickable = false // disable button from being clicked again

            // clean input
            val cleanedRoomID = cleanRoomID(binding.editTextPuzzleId.text.toString())

            if (cleanedRoomID.length == 5) {

                val userRoom = Room(profileViewModel.username.value.toString(), cleanedRoomID, null)

                ApiClient.roomAPIService.join(userRoom)
                    .enqueue(object : Callback<RoomResponse> {

                        override fun onResponse(
                            call: Call<RoomResponse>,
                            response: Response<RoomResponse>
                        ) {
                            if (response.isSuccessful) {
                                toolViewModel.addUserToRoom(
                                    cleanedRoomID,
                                    response.body()?.score ?: 0
                                )
                                navController.navigate(R.id.navigate_welcome_to_home)
                                binding.loadingSubmit.isVisible = false
                            } else {
                                binding.loadingSubmit.isVisible = false
                                binding.buttonPuzzleId.isClickable = true
                                Toast.makeText(
                                    activityContext,
                                    "Puzzle does not exist or could not be located",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("joinRoom: $userRoom", response.errorBody().toString())
                            }
                        }


                        override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                            Log.e("joinRoom: $userRoom", "" + t.message)
                        }
                    })
            } else {
                binding.loadingSubmit.isVisible = false // disable loading animation
                Toast.makeText(activityContext,"Error Invalid Room ID", Toast.LENGTH_SHORT).show()
                binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
            }
        }
    }
}
