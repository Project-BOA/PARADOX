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
import com.example.prototype1.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentLoginBinding
import team.boa.paradox.network.LoginProfile
import team.boa.paradox.network.LoginProfileResponse
import team.boa.paradox.viewmodel.ProfileViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var activityContext: Context
    private val viewModel: ProfileViewModel by activityViewModels()

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

                val userLoginProfile = LoginProfile(
                    usernameInput,
                    passwordInput
                )

                ApiClient.loginApiService.login(userLoginProfile)
                    .enqueue(object : Callback<LoginProfileResponse> {


                        override fun onResponse(
                            call: Call<LoginProfileResponse>,
                            response: Response<LoginProfileResponse>
                        ) {
                            if (response.isSuccessful) {
                                Log.e("login: $userLoginProfile", response.body().toString())
                                viewModel.setUserLoggedIn(
                                    userLoginProfile.username,
                                    "",
                                    response.body()?.biography ?: "No Biography"
                                )
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.navigate_login_to_profile)
                            } else {
                                binding.buttonLogin.isClickable = true
                                Toast.makeText(
                                    binding.root.context,
                                    response.errorBody().toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("login: $userLoginProfile", response.errorBody().toString())
                            }
                            binding.loadingLogin.isVisible = false
                        }


                        override fun onFailure(call: Call<LoginProfileResponse>, t: Throwable) {
                            Log.e("login: $userLoginProfile", "" + t.message)
                        }
                    })
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