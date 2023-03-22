package ru.khozyainov.inspirationpointtesttask.data.repository

import kotlinx.coroutines.flow.Flow
import ru.khozyainov.inspirationpointtesttask.model.Participant

interface ParticipantRepository {
    fun getParticipant(): Flow<List<Participant>>
    suspend fun updateParticipant(participant: Participant)
}