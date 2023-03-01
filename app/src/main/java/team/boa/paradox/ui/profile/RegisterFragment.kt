package team.boa.paradox.ui.profile

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
import androidx.navigation.Navigation
import team.boa.paradox.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentRegisterBinding
import team.boa.paradox.network.RegisterProfile
import team.boa.paradox.network.RegisterProfileResponse
import team.boa.paradox.viewmodel.ProfileViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var activityContext: Context
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        activity?.title = "Register"
        if (container != null) {
            activityContext = container.context
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSubmitRegister.setOnClickListener() {

            binding.loadingRegister.isVisible = true
            binding.buttonSubmitRegister.isClickable = false

            val usernameInput = binding.editTextRegisterUsername.text?.trim().toString()
            val passwordInput =  binding.editTextRegisterPassword.text?.trim().toString()
            val biography =  binding.editTextRegisterBiography.text?.trim().toString()

            if(usernameInput.isNotEmpty() && passwordInput.isNotEmpty() && biography.isNotEmpty()) {
                val userRegister = RegisterProfile(usernameInput, passwordInput, biography)

                ApiClient.registerProfileAPIService.register(userRegister)
                    .enqueue(object : Callback<RegisterProfileResponse> {

                        override fun onResponse(
                            call: Call<RegisterProfileResponse>,
                            response: Response<RegisterProfileResponse>
                        ) {
                            Toast.makeText(activityContext, "Register: " + (response.body()?.status ?: response.errorBody()?.string() ?: "null"), Toast.LENGTH_LONG).show()
                            if (response.isSuccessful) {
                                Navigation.findNavController(view).navigate(R.id.navigate_register_to_login)
                                Log.e("register: $userRegister", response.body().toString())
                            } else {
                                binding.buttonSubmitRegister.isClickable = true
                                Log.d("register: $userRegister", response.raw().toString())
                            }
                            binding.loadingRegister.isVisible = false
                        }

                        override fun onFailure(call: Call<RegisterProfileResponse>, t: Throwable) {
                            Log.e("register: $userRegister", ""+t.message)
                        }
                    })
            }
            else {
                Toast.makeText(activityContext, "Input required", Toast.LENGTH_LONG).show()
                binding.buttonSubmitRegister.isClickable = true
                binding.loadingRegister.isVisible = false
            }
        }
    }
}