package ru.khozyainov.inspirationpointtesttask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.khozyainov.inspirationpointtesttask.data.repository.DataStoreRepository
import ru.khozyainov.inspirationpointtesttask.data.repository.ParticipantRepository
import ru.khozyainov.inspirationpointtesttask.model.Participant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val participantRepository: ParticipantRepository,
    private val dataStoreRepositoryRepository: DataStoreRepository,
): ViewModel() {

    val dbCompleted = dataStoreRepositoryRepository.bdCompleted()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val participant = participantRepository.getParticipant()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), listOf())

    fun updateParticipant(participant: Participant){
        runBlocking {
            participantRepository.updateParticipant(participant)
        }
    }

    fun fillOutDB(){
        viewModelScope.launch {
            dataStoreRepositoryRepository.fillOutDB()
        }
    }
}