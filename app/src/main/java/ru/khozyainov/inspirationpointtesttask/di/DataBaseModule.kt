package ru.khozyainov.inspirationpointtesttask.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.khozyainov.inspirationpointtesttask.data.db.InspirationPointDataBase
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Application): InspirationPointDataBase = Room.databaseBuilder(
        context,
        InspirationPointDataBase::class.java,
        InspirationPointDataBase.DB_NAME
    ).build()

    @Provides
    fun providesPhotoDAO(db: InspirationPointDataBase): ParticipantDao = db.participantDao()

}