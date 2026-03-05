package acr.browser.lightning.ui.agreement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import acr.browser.lightning.network.graphql.GraphQlDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ConsentUiState {
    data object Idle : ConsentUiState
    data object Loading : ConsentUiState
    data object Success : ConsentUiState
    data class Error(val message: String) : ConsentUiState
}

class AgreementViewModel @Inject constructor(
    private val graphQlDataSource: GraphQlDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConsentUiState>(ConsentUiState.Idle)
    val uiState: StateFlow<ConsentUiState> = _uiState

    fun sendConsent(userId: String, deviceId: String) {
        if (_uiState.value is ConsentUiState.Loading) return

        _uiState.value = ConsentUiState.Loading
        viewModelScope.launch {
            try {
                graphQlDataSource.sendUserConsent(
                    userId = userId,
                    deviceId = deviceId,
                    consentGiven = 1
                )
                _uiState.value = ConsentUiState.Success
            } catch (e: Exception) {
                _uiState.value = ConsentUiState.Error(
                    e.message ?: "Failed to send consent. Please try again."
                )
            }
        }
    }
}
