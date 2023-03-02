package team.boa.paradox.ui.tool

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentSubmitToolBinding
import team.boa.paradox.network.*
import team.boa.paradox.viewmodel.ProfileViewModel
import team.boa.paradox.viewmodel.ToolViewModel

class SubmitToolFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val toolViewModel: ToolViewModel by activityViewModels()
    private lateinit var binding: FragmentSubmitToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubmitToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Submit Answer"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        binding.submitButton.setOnClickListener {
            navController.navigate(R.id.navigate_submit_to_complete)

            val answerInput = binding.ansCode.text?.trim().toString()

            if (answerInput.isNotEmpty()) {
                val userAnswer = Room(profileViewModel.username.value.toString(),toolViewModel.roomId.value.toString(),answerInput)

                ApiClient.roomAPIService.submit(userAnswer)
                    .enqueue( object : Callback<RoomResponse> {

                        override fun onResponse (
                            call: Call<RoomResponse>,
                            response: Response<RoomResponse>
                        ) {
                            // Toast the response status
                            Toast.makeText(activityContext, response.body()?.status ?: "Error", Toast.LENGTH_LONG).show()

                        }

                        override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                            Log.e("Submit: $userAnswer", ""+t.message)
                        }
                    })
            } else {
                Toast.makeText(activityContext, "Input required", Toast.LENGTH_LONG).show()
            }
        }
        }
    }