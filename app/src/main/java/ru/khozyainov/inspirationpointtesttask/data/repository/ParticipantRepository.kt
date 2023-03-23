package ru.khozyainov.inspirationpointtesttask.data.repository

import kotlinx.coroutines.flow.Flow
import ru.khozyainov.inspirationpointtesttask.model.Participant

interface ParticipantRepository {
    fun getParticipants(): Flow<List<Participant>>
    suspend fun updateParticipants(participants: List<Participant>)
}