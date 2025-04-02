package rs.raf.rafeisen.utils

import kotlinx.serialization.Serializable

@Serializable
data class Pageable<T>(
    val content: List<T>,
    val page: PageInfo,
)

@Serializable
data class PageInfo(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
)
