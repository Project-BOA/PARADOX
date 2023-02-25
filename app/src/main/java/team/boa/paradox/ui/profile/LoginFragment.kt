package team.boa.paradox.ui.profile

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
import team.boa.paradox.network.profile.Profile
import team.boa.paradox.network.profile.ProfileResponse
import team.boa.paradox.viewmodel.ProfileViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener() {

            binding.loadingLogin.isVisible = true;

            val userProfile = Profile(
                binding.editTextLoginUsername.text.toString(),
                binding.editTextLoginPassword.text.toString()
            )

            ApiClient.loginApiService.validateLogin(userProfile)
                .enqueue(object : Callback<ProfileResponse> {


                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.e("validateLogin: $userProfile", response.body().toString())
                                viewModel.setUserLoggedIn(userProfile.username, "", response.body()?.biography ?: "No Biography")
                            binding.loadingLogin.isVisible = false;
                            Navigation.findNavController(binding.root).navigate(R.id.navigate_login_to_profile)
                        }
                        else {
                            binding.loadingLogin.isVisible = true;
                            Toast.makeText(
                                binding.root.context,
                                response.errorBody().toString(),
                                Toast.LENGTH_SHORT
                            ).show();
                            Log.d("validateLogin: $userProfile", response.errorBody().toString())
                        }
                    }


                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Log.e("validateLogin: $userProfile", ""+t.message)
                    }
                })
        }

        binding.buttonRegister.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.navigate_login_to_register)
        }
    }
}