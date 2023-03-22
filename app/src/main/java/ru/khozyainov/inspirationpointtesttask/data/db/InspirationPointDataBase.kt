package ru.khozyainov.inspirationpointtesttask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.khozyainov.inspirationpointtesttask.data.db.InspirationPointDataBase.Companion.DB_VERSION

@Database(
    entities =[
        ParticipantEntity::class
    ],
    version = DB_VERSION
)
abstract class InspirationPointDataBase: RoomDatabase() {

    abstract fun participantDao(): ParticipantDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "inspiration_point-database"
    }
}