package team.boa.paradox.ui.tool

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.databinding.FragmentNotesToolBinding
import team.boa.paradox.viewmodel.ToolViewModel

class NotesToolFragment : Fragment() {

    private val toolViewModel: ToolViewModel by activityViewModels()
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
        val sharedPreferences = activityContext.getSharedPreferences("Notes", Context.MODE_PRIVATE)
        val roomID = toolViewModel.room.value?.roomID ?: "DEFAULT"

        binding.noteText.setText(sharedPreferences.getString(roomID, ""))

        binding.saveButton.setOnClickListener{
            sharedPreferences
                .edit()
                .putString(roomID, binding.noteText.text.toString())
                .apply()
        }
    }
}
