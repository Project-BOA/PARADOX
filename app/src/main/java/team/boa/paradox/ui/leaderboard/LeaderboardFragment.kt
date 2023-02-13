package team.boa.paradox.ui.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import team.boa.paradox.R
import team.boa.paradox.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderboardFragment()
    }

    private lateinit var viewModel: LeaderboardViewModel
    private lateinit var binding: FragmentLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LeaderboardViewModel::class.java]
        binding = FragmentLeaderboardBinding.inflate(layoutInflater)
        // TODO: Use the ViewModel
    }

}