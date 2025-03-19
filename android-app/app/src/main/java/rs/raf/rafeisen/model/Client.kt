package rs.raf.rafeisen.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi

@Entity
data class Client
    @OptIn(ExperimentalUuidApi::class)
    constructor(
        @PrimaryKey
        val id: String,
        val firstName: String,
        val lastName: String,
        val gender: Gender,
        val email: String,
        val phone: String,
        val address: String,
        // TODO: add privileges and dateOfBirth
    )
