package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentSubmitToolBinding
import team.boa.paradox.network.*
import team.boa.paradox.viewmodel.RoomViewModel

class SubmitToolFragment : Fragment() {

    private val toolData: RoomViewModel by activityViewModels()
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

    private fun submitAnswer(answer: String, navController: NavController) {
        val userAnswer = toolData.getRoom()!!
        userAnswer.answer = answer

        val client = ApiClient.roomAPIService.submit(userAnswer)

        val handler = object : Callback<RoomResponse> {

            override fun onResponse (
                call: Call<RoomResponse>,
                response: Response<RoomResponse>
            ) {
                if (isAdded) {
                    val status = response.body()?.status ?: "Error"

                    // Toast the response status
                    Toast.makeText(
                        activityContext,
                        status,
                        Toast.LENGTH_LONG
                    ).show()

                    Log.i("submit", response.body().toString())

                    if (status.contains("OK"))
                        navController.navigate(R.id.navigate_submit_to_complete)
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                if (isAdded) {
                    Log.e("Submit: $userAnswer", "" + t.message)
                }
            }
        }

        client.enqueue(handler)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

        binding.submitButton.setOnClickListener {
            val answerInput = binding.ansCode.text?.trim().toString()

            if (answerInput.isEmpty()) {
                Toast.makeText(activityContext, "Input required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (toolData.isInRoom.value == false) {
                Toast.makeText(activityContext, "Error not in a room", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            submitAnswer(answerInput, navController)
        }
    }
}