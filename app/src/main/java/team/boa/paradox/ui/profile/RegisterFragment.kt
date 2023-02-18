package team.boa.paradox.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import team.boa.paradox.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSubmitRegister.setOnClickListener() {
            viewModel.register(binding.editTextRegisterUsername.text.toString(), binding.editTextRegisterPassword.text.toString(), binding.editTextRegisterEmail.text.toString())
            // launch login view on success or error toast on failure
        }
    }
}