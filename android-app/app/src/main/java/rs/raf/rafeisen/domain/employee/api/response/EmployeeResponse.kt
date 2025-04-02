package rs.raf.rafeisen.domain.employee.api.response

import java.time.LocalDate
import kotlinx.serialization.Serializable
import rs.raf.rafeisen.domain.client.model.Gender
import rs.raf.rafeisen.serialization.custom.LocalDateSerializer

@Serializable
data class EmployeeResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    @Serializable(with = LocalDateSerializer::class)
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val email: String,
    val phone: String,
    val address: String,
    val username: String,
    val position: String,
    val department: String,
    val active: Boolean,
)
