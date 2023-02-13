package team.boa.paradox.ui.home

import android.R
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import team.boa.paradox.databinding.FragmentHomeBinding
import team.boa.paradox.ui.tools.CameraActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private fun attachListeners() {
        binding.cameraButton.setOnClickListener{
            startActivity(Intent(view?.context, CameraActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        attachListeners()
    }
}