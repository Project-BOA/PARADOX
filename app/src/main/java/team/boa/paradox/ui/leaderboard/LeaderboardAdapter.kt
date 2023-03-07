package team.boa.paradox.ui.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import team.boa.paradox.databinding.RecyclerViewLeaderboardEntryBinding
import team.boa.paradox.network.LeaderboardEntry

class LeaderboardAdapter (
    val leaderboardEntry: List<LeaderboardEntry>
) : RecyclerView.Adapter<LeaderboardAdapter.MainViewHolder>() {

    private lateinit var binding: RecyclerViewLeaderboardEntryBinding

    inner class MainViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(leaderboardEntry: LeaderboardEntry) {
            binding.leaderboardEntryPosition.text = leaderboardEntry.position
            binding.leaderboardEntryName.text = leaderboardEntry.name
            binding.leaderboardEntryScore.text = leaderboardEntry.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = RecyclerViewLeaderboardEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(leaderboardEntry[position])
    }

    override fun getItemCount(): Int {
        return leaderboardEntry.size
    }
}