package team.boa.paradox.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentHomeBinding
import team.boa.paradox.viewmodel.RoomViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val roomViewModel: RoomViewModel by activityViewModels()
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
        navController = Navigation.findNavController(requireView())
        setHasOptionsMenu(true)

        if (roomViewModel.isInRoom.value == false) {
            navController.navigate(R.id.navigate_home_to_welcome)
        }

        // attach listeners
        binding.cameraButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_camera)
        }
        binding.cameraText.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_camera)
        }


        binding.cipherButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_cipher)
        }
        binding.cipherText.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_cipher)
        }

        binding.converterButton.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_converter)
        }
        binding.converterText.setOnClickListener{
            navController.navigate(R.id.navigate_home_to_converter)
        }


        binding.morseButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_morse)
        }
        binding.morseText.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_morse)
        }

        binding.notesButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_notes)
        }        
        binding.notesText.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_notes)
        }
        binding.submitButton.setOnClickListener {
            navController.navigate(R.id.navigate_home_to_submit)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean  = when (item.itemId) {
        R.id.button_leave -> {
            if (roomViewModel.isInRoom.value == true) {
                roomViewModel.leave(activityContext)
                navController.navigate(R.id.navigate_home_to_welcome)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}