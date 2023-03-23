package ru.khozyainov.inspirationpointtesttask.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantDao
import ru.khozyainov.inspirationpointtesttask.model.Participant
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ParticipantRepositoryImpl @Inject constructor(
    private val participantDao: ParticipantDao
) : ParticipantRepository {

    override fun getParticipants(): Flow<List<Participant>> =
        participantDao.getParticipants()
            .mapLatest { entities ->
                entities.map { it.toParticipant() }
            }

    override suspend fun updateParticipants(participants: List<Participant>) = withContext(Dispatchers.IO) {
        participantDao.updateParticipants(participants.map { it.toParticipantEntity() })
    }
}