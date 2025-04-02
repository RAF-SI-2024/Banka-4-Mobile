package rs.raf.rafeisen.domain.client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    @SerialName("MALE")
    Male,

    @SerialName("FEMALE")
    Female,
}
