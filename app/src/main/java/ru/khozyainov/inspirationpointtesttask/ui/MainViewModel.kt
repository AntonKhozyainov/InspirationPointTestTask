package ru.khozyainov.inspirationpointtesttask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.khozyainov.inspirationpointtesttask.data.repository.DataStoreRepository
import ru.khozyainov.inspirationpointtesttask.data.repository.ParticipantRepository
import ru.khozyainov.inspirationpointtesttask.model.Participant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val participantRepository: ParticipantRepository,
    private val dataStoreRepositoryRepository: DataStoreRepository,
) : ViewModel() {

    val dbCompleted = dataStoreRepositoryRepository.bdCompleted()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _participants = MutableStateFlow<List<Participant>>(listOf())
    val participants: StateFlow<List<Participant>> = _participants

    init {
        participantRepository.getParticipants()
            .onEach { participants ->
                _participants.value = setRankedAndSorted(participants)
            }
            .launchIn(viewModelScope)
    }

    fun updateListParticipants(participant: Participant) {
        _participants.value = setRankedAndSorted(_participants.value.map {
            updateParticipant(it, participant)
        })
        saveParticipants()
    }

    fun fillOutDB() =
        viewModelScope.launch { dataStoreRepositoryRepository.fillOutDB() }

    private fun saveParticipants() =
        viewModelScope.launch { participantRepository.updateParticipants(_participants.value) }

    private fun updateParticipant(old: Participant, new: Participant): Participant =
        if (old.id == new.id) new
        else old

    private fun setRankedAndSorted(participants: List<Participant>): List<Participant> {
        val upAmountOfPoints = updateAmountOfPoints(participants)
        val upRanked = if (tableIsFull(upAmountOfPoints)) setRanked(upAmountOfPoints)
        else upAmountOfPoints
        return upRanked.sortedBy { it.id }
    }

    private fun setRanked(participants: List<Participant>): List<Participant> =
        participants.sortedByDescending { it.amountOfPoints }.mapIndexed { index, participant ->
            participant.copy(ranked = index + 1)
        }

    private fun tableIsFull(participants: List<Participant>): Boolean =
        participants.none { it.amountOfPoints == 0 }

    private fun updateAmountOfPoints(participants: List<Participant>): List<Participant> =
        participants.map { updateAmountOfPoints(it) }

    private fun updateAmountOfPoints(participant: Participant): Participant =
        participant.copy(amountOfPoints = getAmountOfPoints(participant))

    private fun getAmountOfPoints(participant: Participant): Int {
        var filled = 0
        var result = 0

        var pair = checkField(participant.field1)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field2)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field3)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field4)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field5)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field6)
        filled += pair.first
        result += pair.second

        pair = checkField(participant.field7)
        filled += pair.first
        result += pair.second


        return if (filled == 6) result
        else 0
    }

    private fun checkField(field: Int?): Pair<Int, Int> =
        if (field != null && field in 0..5) 1 to field
        else 0 to 0
}