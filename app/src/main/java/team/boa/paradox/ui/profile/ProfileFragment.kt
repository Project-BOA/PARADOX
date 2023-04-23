package team.boa.paradox.ui.profile

import android.content.Intent
import android.net.Uri
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
import team.boa.paradox.viewmodel.RoomViewModel


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileData: ProfileViewModel by activityViewModels()
    private val roomData: RoomViewModel by activityViewModels()

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

        setupLinkButton()

        // launch login view if not already logged in
        if (profileData.isLoggedIn.value == false) {
            Navigation.findNavController(requireView()).navigate(R.id.navigate_profile_to_login)
        }

        binding.textProfileUsername.text = profileData.getProfile()?.username
        binding.textProfileBiography.text = profileData.getProfile()?.biography


        binding.buttonEdit.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.navigate_profile_to_edit)
        }

        binding.buttonLogout.setOnClickListener {
            profileData.logout(view.context)
            roomData.leave(view.context)
            Navigation.findNavController(requireView()).navigate(R.id.navigate_profile_to_login)
        }
    }

    fun setupLinkButton() {
        binding.linkToSite.paint?.isUnderlineText = true;
        binding.linkToSite.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://paradox-kappa.vercel.app/")
            )
            startActivity(browserIntent)
        }
    }
}