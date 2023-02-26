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
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentSubmitToolBinding
import team.boa.paradox.viewmodel.ToolViewModel

class SubmitToolFragment : Fragment() {

    private val toolViewModel: ToolViewModel by activityViewModels()
    private lateinit var binding: FragmentSubmitToolBinding
    private lateinit var activityContext: Context
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubmitToolBinding.inflate(inflater, container, false)
        if (container != null) {
            activityContext = container.context
        }
        activity?.title = "Submit Answer"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)

        binding.submitButton.setOnClickListener {
            navController.navigate(R.id.navigate_submit_to_complete)
        }
    }
}