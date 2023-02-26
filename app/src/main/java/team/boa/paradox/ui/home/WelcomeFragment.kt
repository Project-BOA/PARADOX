package team.boa.paradox.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentWelcomeBinding
import team.boa.paradox.viewmodel.ToolViewModel

class WelcomeFragment : Fragment() {

    private lateinit var database : DatabaseReference
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController
    private val toolViewModel: ToolViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Join Room"
        return binding.root
    }

    // clean a string to match puzzle id format
    private fun cleanPuzzleId(inputString: String): String {
        return inputString
            .uppercase() // convert input to uppercase if not already
            .replace("[^0-9A-Z ]".toRegex(), "") // only allow numbers, capital letters and spaces
            .trim() // trim any excess spaces
    }

    // get the puzzle id in the database
    private fun getRoomFromID(view: View, roomId:String) {
        database = FirebaseDatabase.getInstance().getReference("room")
        database.child(roomId).get().addOnSuccessListener {
            if (it.exists()) {
                toolViewModel.addUserToRoom(roomId, 0)
                navController.navigate(R.id.navigate_welcome_to_home)
            }
            else {
                binding.loadingSubmit.isVisible = false // disable loading animation
                binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
                Toast.makeText(activityContext,"Puzzle does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener() {
            binding.loadingSubmit.isVisible = false // disable loading animation
            binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
            Toast.makeText(activityContext,"Error could not locate Puzzle Id", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        binding.buttonPuzzleId.setOnClickListener {
            binding.loadingSubmit.isVisible = true // enable loading animation
            binding.buttonPuzzleId.isClickable = false // disable button from being clicked again

            // clean input
            val cleanedPuzzleId = cleanPuzzleId(binding.editTextPuzzleId.text.toString())

            if (cleanedPuzzleId.length < 3) {
                binding.loadingSubmit.isVisible = false // disable loading animation
                Toast.makeText(activityContext,"Error Invalid Puzzle Id", Toast.LENGTH_SHORT).show()
                binding.buttonPuzzleId.isClickable = true // enable button to allow for retry
            }

            if (cleanedPuzzleId.length >= 3) {
                // lookup puzzle_id
                getRoomFromID(view, cleanedPuzzleId)
            }
        }
    }
}