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

        // getting data from profile view model
        binding.textProfileUsernameEdit.text = profileData.getProfile()?.username
//        binding.editTextEditBiography.text = profileData.getProfile()?.biography

        binding.buttonSubmitEdit.setOnClickListener {

            binding.loadingEdit.isVisible = true
            binding.buttonSubmitEdit.isClickable = false

            // username from profile viewmodel
            val username = profileData.getProfile()?.username.toString()

            // User can change email, biography and password
            val email =  binding.editTextEditEmail.text?.trim().toString()
            val biography =  binding.editTextEditBiography.text?.trim().toString()
            val newpasswordInput =  binding.editTextEditNewPassword.text?.trim().toString()

            // User needs to confirm their password before editing
            val confirmPassword =  binding.editTextEditPassword.text?.trim().toString()

            // check that confirmation password given and at least other input given
            if (confirmPassword != "null" && (username != "null" || biography != "null"|| newpasswordInput != "null")) {
                val editedProfile = Profile(username, confirmPassword, email, biography, newpasswordInput)

                ApiClient.profileAPIService.edit(editedProfile).enqueue (
                object : Callback<ProfileResponse> {

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
                                Log.d("Edit: $editedProfile", response.body().toString())
                            } else {
                                binding.buttonSubmitEdit.isClickable = true
                                Log.e("Edit: $editedProfile", response.errorBody()!!.string())
                            }
                            binding.loadingEdit.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        if (isAdded) {
                            Log.e("Edit: $editedProfile", "" + t.message)
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