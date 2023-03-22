package ru.khozyainov.inspirationpointtesttask.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantDao
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntity
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val participantDao: ParticipantDao
):DataStoreRepository {

    override fun bdCompleted(): Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[DB_COMPLETED] ?: false
            }

    override suspend fun fillOutDB() = withContext(Dispatchers.IO) {
        (1..7).forEach {
            participantDao.insertParticipant(
                ParticipantEntity(
                    id = it
                )
            )
        }
        saveDdCompletedState(true)
    }

    private suspend fun saveDdCompletedState(completed: Boolean) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[DB_COMPLETED] = completed
            }
        }
    }

    companion object {
        private val DB_COMPLETED = booleanPreferencesKey("DB_COMPLETED")
    }
}