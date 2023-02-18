package team.boa.paradox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import team.boa.paradox.MainActivity
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // launch login view if not already logged in
        if (viewModel.isLoggedIn.value == false) {
            parentFragmentManager.beginTransaction()
                .add(
                    (activity as MainActivity).findViewById<View>(R.id.nav_host_fragment_activity_main).id, LoginFragment()
                )
                .commit()
        }

        binding.textProfileUsername.text = viewModel.username.value
        binding.textProfileBiography.text = viewModel.biography.value

        binding.buttonLogout.setOnClickListener() {
            viewModel.logout()
        }
    }
}