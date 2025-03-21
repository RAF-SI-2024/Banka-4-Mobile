package rs.raf.rafeisen.navigation

import androidx.lifecycle.SavedStateHandle


const val SECRET = "secret"
inline val SavedStateHandle.secretOrNull: String? get() = get(SECRET)

const val ISSUER = "issuer"
inline val SavedStateHandle.issuerOrNull: String? get() = get(ISSUER)
