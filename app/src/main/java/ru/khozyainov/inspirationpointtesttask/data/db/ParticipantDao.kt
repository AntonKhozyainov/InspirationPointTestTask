package ru.khozyainov.inspirationpointtesttask.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.ID
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.TABLE_NAME

@Dao
interface ParticipantDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ID")
    fun getParticipants(): Flow<List<ParticipantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: ParticipantEntity)

    @Update
    suspend fun updateParticipant(participant: ParticipantEntity)
}