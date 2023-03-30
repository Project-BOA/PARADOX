package team.boa.paradox.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentLoginBinding
import team.boa.paradox.network.ApiClient
import team.boa.paradox.network.Profile
import team.boa.paradox.network.ProfileResponse
import team.boa.paradox.viewmodel.ProfileViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var activityContext: Context
    private val profileData: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        activity?.title = "Login or Register"
        if (container != null) {
            activityContext = container.context
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {

            val usernameInput = binding.editTextLoginUsername.text?.trim().toString()
            val passwordInput =  binding.editTextLoginPassword.text?.trim().toString()

            binding.loadingLogin.isVisible = true
            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()) {

                val userLoginProfile = Profile (
                    usernameInput,
                    passwordInput,
                    null,
                    null,
                    null
                )

                // call login api giving the user login with response handler object
                ApiClient.profileAPIService.login(userLoginProfile).enqueue (
                object : Callback<ProfileResponse> {

                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if (isAdded) {
                            // Toast the response status
                            Toast.makeText(activityContext, response.body()?.status ?: "Error", Toast.LENGTH_LONG).show()

                            if (response.isSuccessful) {
                                profileData.login(activityContext, Profile(
                                    usernameInput,
                                    passwordInput,
                                    response.body()?.email ?: "No Email",
                                    response.body()?.biography ?: "No Biography",
                                    response.body()?.newPassword ?: "No NewPassword"
                                ))
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.navigate_login_to_profile)
                                Log.d("login: $userLoginProfile", response.body().toString())

                            } else {
                                binding.buttonLogin.isClickable = true
                                Log.e("login: $userLoginProfile", response.body().toString())
                            }
                            binding.loadingLogin.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        if (isAdded) {
                            Log.e("login: $userLoginProfile", "" + t.message)
                        }
                    }
                }
            )
            } else {
                Toast.makeText(activityContext, "Input required", Toast.LENGTH_LONG).show()
                binding.buttonLogin.isClickable = true
                binding.loadingLogin.isVisible = false
            }
        }

        binding.buttonRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.navigate_login_to_register)
        }
    }
}