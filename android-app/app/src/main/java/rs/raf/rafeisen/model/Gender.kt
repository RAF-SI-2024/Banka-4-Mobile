package rs.raf.rafeisen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    @SerialName("MALE")
    Male,

    @SerialName("FEMALE")
    Female,
}
