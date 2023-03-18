package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.databinding.FragmentNotesToolBinding
import team.boa.paradox.viewmodel.RoomViewModel

class NotesToolFragment : Fragment() {

    private val roomViewModel: RoomViewModel by activityViewModels()
    private lateinit var binding: FragmentNotesToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Notes"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        val roomSharedPreferences = activityContext.getSharedPreferences("Room", Context.MODE_PRIVATE)

        binding.noteText.setText(roomSharedPreferences.getString("Notes", ""))

        binding.saveButton.setOnClickListener{
            roomSharedPreferences
                .edit()
                .putString("Notes", binding.noteText.text.toString())
                .apply()
        }
    }
}
