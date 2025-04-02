package rs.raf.rafeisen.totp.home

import rs.raf.rafeisen.totp.model.TotpUiModel

interface TotpContract {
    data class UiState(val totpCodes: Map<String, TotpUiModel> = emptyMap(), val isLoading: Boolean = true)
}
