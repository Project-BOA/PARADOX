package team.boa.paradox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener() {
            val success = viewModel.authenticate(binding.editTextLoginUsername.text.toString(), binding.editTextLoginPassword.text.toString())

            if (success) {
                Navigation.findNavController(binding.root).navigate(R.id.navigate_login_to_profile)
            } else {
                Toast.makeText(binding.root.context, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
            }
        }

        binding.buttonRegister.setOnClickListener() {
            Navigation.findNavController(binding.root).navigate(R.id.navigate_login_to_register)
        }
    }
}