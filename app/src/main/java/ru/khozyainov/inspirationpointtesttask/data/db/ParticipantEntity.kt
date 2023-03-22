package ru.khozyainov.inspirationpointtesttask.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.AMOUNT_OF_POINTS
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_1
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_2
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_3
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_4
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_5
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_6
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.FIELD_7
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.ID
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.Columns.RANKED
import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntityContract.TABLE_NAME
import ru.khozyainov.inspirationpointtesttask.model.Participant

@Entity(
    tableName = TABLE_NAME,
    indices = [
        Index(ID)
    ]
)
data class ParticipantEntity(
    @PrimaryKey
    @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = AMOUNT_OF_POINTS) val amountOfPoints: Int = 0,
    @ColumnInfo(name = RANKED) val ranked: Int = 0,
    @ColumnInfo(name = FIELD_1) val field1: Int? = null,
    @ColumnInfo(name = FIELD_2) val field2: Int? = null,
    @ColumnInfo(name = FIELD_3) val field3: Int? = null,
    @ColumnInfo(name = FIELD_4) val field4: Int? = null,
    @ColumnInfo(name = FIELD_5) val field5: Int? = null,
    @ColumnInfo(name = FIELD_6) val field6: Int? = null,
    @ColumnInfo(name = FIELD_7) val field7: Int? = null,
) {
    fun toParticipant(): Participant =
        Participant(
            id = this.id,
            amountOfPoints = this.amountOfPoints,
            ranked = this.ranked,
            field1 = this.field1,
            field2 = this.field2,
            field3 = this.field3,
            field4 = this.field4,
            field5 = this.field5,
            field6 = this.field6,
            field7 = this.field7,
        )
}
