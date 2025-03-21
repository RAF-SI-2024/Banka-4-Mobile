package rs.raf.rafeisen.screen.home

import rs.raf.rafeisen.totp.model.TotpUiModel

interface HomeContract {
    data class UiState(
        val totpCodes: Map<String, TotpUiModel> = emptyMap(),
        val isLoading: Boolean = true,
    )
}
