package ru.khozyainov.inspirationpointtesttask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.khozyainov.inspirationpointtesttask.data.repository.DataStoreRepository
import ru.khozyainov.inspirationpointtesttask.data.repository.DataStoreRepositoryImpl
import ru.khozyainov.inspirationpointtesttask.data.repository.ParticipantRepository
import ru.khozyainov.inspirationpointtesttask.data.repository.ParticipantRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun provideParticipantRepository(participantRepositoryImpl: ParticipantRepositoryImpl): ParticipantRepository

    @Binds
    abstract fun provideDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository
}