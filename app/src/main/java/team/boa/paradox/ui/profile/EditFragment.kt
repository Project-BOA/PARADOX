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
import team.boa.paradox.databinding.FragmentEditBinding
import team.boa.paradox.network.ApiClient
import team.boa.paradox.network.Profile
import team.boa.paradox.network.ProfileResponse
import team.boa.paradox.viewmodel.ProfileViewModel

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var activityContext: Context
    private val profileData: ProfileViewModel by activityViewModels()

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        activity?.title = "Edit"
        if (container != null) {
            activityContext = container.context
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (profileData.isLoggedIn.value == false) {
            Navigation.findNavController(requireView()).navigate(R.id.navigate_profile_to_login)
        }
        binding.buttonSubmitEdit.setOnClickListener {

            binding.loadingEdit.isVisible = true
            binding.buttonSubmitEdit.isClickable = false

            val usernameInput = profileData.getProfile()?.username.toString()
            val passwordInput =  binding.editTextEditPassword.text?.trim().toString()
            val newpasswordInput =  binding.editTextEditNewPassword.text?.trim().toString()
            val biography =  binding.editTextEditBiography.text?.trim().toString()

            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()&& newpasswordInput.isNotEmpty() && biography.isNotEmpty()) {
                val userEdit = Profile(usernameInput, passwordInput, null, biography,newpasswordInput)

                ApiClient.profileAPIService.edit(userEdit)
                    .enqueue( object : Callback<ProfileResponse> {

                        override fun onResponse (
                            call: Call<ProfileResponse>,
                            response: Response<ProfileResponse>
                        ) {
                            if (isAdded) {
                                // Toast the response status
                                Toast.makeText(
                                    activityContext,
                                    response.body()?.status ?: "Error",
                                    Toast.LENGTH_LONG
                                ).show()

                                if (response.isSuccessful) {
                                    Navigation.findNavController(requireView())
                                        .navigate(R.id.profile_edit_to_profile)
                                    Log.d("Edit: $userEdit", response.body().toString())
                                } else {
                                    binding.buttonSubmitEdit.isClickable = true
                                    Log.e("Edit: $userEdit", response.raw().toString())
                                }
                                binding.loadingEdit.isVisible = false
                            }
                        }

                        override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                            if (isAdded) {
                                Log.e("Edit: $userEdit", "" + t.message)
                            }
                        }
                    })
            } else {
                Toast.makeText(activityContext, "Input required", Toast.LENGTH_LONG).show()
                binding.buttonSubmitEdit.isClickable = true
                binding.loadingEdit.isVisible = false
            }
        }
    }
}