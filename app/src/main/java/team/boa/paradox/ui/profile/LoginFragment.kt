package team.boa.paradox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import team.boa.paradox.MainActivity
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentLoginBinding
import team.boa.paradox.databinding.ActivityMainBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainBinding: ActivityMainBinding
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
            // launch login view on success or error toast on failure
            if (success) {
                parentFragmentManager.beginTransaction()
                    .add(
                        (activity as MainActivity).findViewById<View>(R.id.nav_host_fragment_activity_main).id, ProfileFragment()
                    )
                    .commit()
            }
        }

        binding.buttonRegister.setOnClickListener() {
            // launch register fragment
        }
    }
}