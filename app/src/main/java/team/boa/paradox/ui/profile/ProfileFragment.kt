package team.boa.paradox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentProfileBinding
import team.boa.paradox.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileData: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        activity?.title = "Profile"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // launch login view if not already logged in
        if (profileData.isLoggedIn.value == false) {
            Navigation.findNavController(requireView()).navigate(R.id.navigate_profile_to_login)
        }

        binding.textProfileUsername.text = profileData.getProfile()?.username
        binding.textProfileBiography.text = profileData.getProfile()?.biography

        binding.buttonLogout.setOnClickListener {
            profileData.logout()
            Navigation.findNavController(requireView()).navigate(R.id.navigate_profile_to_login)
        }
    }
}