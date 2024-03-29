package team.boa.paradox.ui.home

import android.content.Context
import android.nfc.tech.NfcV
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentAnswerCorrectBinding
import team.boa.paradox.viewmodel.RoomViewModel

class AnswerCorrectFragment : Fragment() {

    private val roomViewModel: RoomViewModel by activityViewModels()
    private lateinit var binding: FragmentAnswerCorrectBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnswerCorrectBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Answer Correct"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

        binding.buttonClose.setOnClickListener {
            navController.navigate(R.id.complete_to_home)
        }
    }
}