package ru.khozyainov.inspirationpointtesttask.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun bdCompleted(): Flow<Boolean>
    suspend fun fillOutDB()
}