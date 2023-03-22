package ru.khozyainov.inspirationpointtesttask.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantDao
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntity
import ru.khozyainov.inspirationpointtesttask.model.Participant
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ParticipantRepositoryImpl @Inject constructor(
    private val participantDao: ParticipantDao
) : ParticipantRepository {

    override fun getParticipant(): Flow<List<Participant>> =
        participantDao.getParticipants()
            .mapLatest { entities ->
                val res = if (tableIsFull(entities)) setRanked(entities).map { it.toParticipant() }
                else entities.map { it.toParticipant() }
                res.sortedBy { it.id }
            }

    override suspend fun updateParticipant(participant: Participant) = withContext(Dispatchers.IO) {
        participantDao.updateParticipant(participant.toParticipantEntity())
    }

    private fun setRanked(entities: List<ParticipantEntity>): List<ParticipantEntity> =
        entities.sortedByDescending { it.amountOfPoints }.mapIndexed { index, participantEntity ->
            participantEntity.copy(ranked = index + 1)
        }

    private fun tableIsFull(entities: List<ParticipantEntity>): Boolean =
        entities.none { it.amountOfPoints == 0 }
}