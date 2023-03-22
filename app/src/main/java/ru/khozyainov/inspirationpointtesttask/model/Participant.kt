package ru.khozyainov.inspirationpointtesttask.model

import ru.khozyainov.inspirationpointtesttask.data.db.ParticipantEntity

data class Participant(
    val id: Int,
    val field1: Int?,
    val field2: Int?,
    val field3: Int?,
    val field4: Int?,
    val field5: Int?,
    val field6: Int?,
    val field7: Int?,
    val amountOfPoints: Int = 0,
    val ranked: Int
) {
    fun toParticipantEntity(): ParticipantEntity =
        ParticipantEntity(
            id = this.id,
            field1 = this.field1,
            field2 = this.field2,
            field3 = this.field3,
            field4 = this.field4,
            field5 = this.field5,
            field6 = this.field6,
            field7 = this.field7,
            amountOfPoints = this.amountOfPoints,
            ranked = this.ranked
        )
}
