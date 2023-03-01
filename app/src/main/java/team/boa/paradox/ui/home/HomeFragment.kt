package team.boa.paradox.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentHomeBinding
import team.boa.paradox.network.Profile
import team.boa.paradox.viewmodel.ProfileViewModel
import team.boa.paradox.viewmodel.ToolViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val toolViewModel: ToolViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Home"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        // launch login view if not already logged in
        //if (profileViewModel.isLoggedIn.value == false) {
          //  navController.navigate(R.id.navigate_home_to_login)
        //}

        if (toolViewModel.isInRoom.value == false) {
            navController.navigate(R.id.navigate_home_to_welcome)
        }

        // attach listeners
        binding.cameraButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_camera)
        }
        binding.cipherButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_cipher)
        }
        binding.converterButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_converter)
        }
        binding.morseButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_morse)
        }
        binding.notesButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_notes)
        }
        binding.submitButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_submit)
        }
    }

}