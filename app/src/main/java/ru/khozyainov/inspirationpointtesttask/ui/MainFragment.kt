package ru.khozyainov.inspirationpointtesttask.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.khozyainov.inspirationpointtesttask.adapter.ParticipantAdapter
import ru.khozyainov.inspirationpointtesttask.databinding.FragmentMainBinding
import ru.khozyainov.inspirationpointtesttask.utils.ViewBindingFragment
import ru.khozyainov.inspirationpointtesttask.utils.autoCleared
import ru.khozyainov.inspirationpointtesttask.utils.launchAndCollectIn

@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private var adapter by autoCleared<ParticipantAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dbCompleted.launchAndCollectIn(viewLifecycleOwner){ completed ->
            if (completed == true){
                initLeagueTable()
                observeStateFlow()
            } else if (completed == false){
                viewModel.fillOutDB()
            }
        }
    }

    private fun observeStateFlow() {
        viewModel.participant.launchAndCollectIn(viewLifecycleOwner) { participants ->
            adapter.items = participants
        }
    }

    private fun initLeagueTable() {
        adapter = ParticipantAdapter { participant ->
            viewModel.updateParticipant(participant)
        }
        binding.leagueTableRecyclerView?.adapter = adapter
        binding.leagueTableRecyclerView?.layoutManager = LinearLayoutManager(context)

    }
}