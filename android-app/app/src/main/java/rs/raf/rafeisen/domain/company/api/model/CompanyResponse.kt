package rs.raf.rafeisen.domain.company.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    val id: String,
    val name: String,
    val tin: String,
    val crn: String,
    val address: String,
    val activityCode: String
)
