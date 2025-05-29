package junkeritechnepal.nicasiacmp.modules.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import junkeritechnepal.nicasiacmp.data.GenericUIState
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkConstants
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    internal val networkService by lazy { NetworkService.INSTANCE }

    internal val _countrySheetState = MutableStateFlow(
        GenericUIState<List<LoginCountryResDto>>(emptyList(), false)
    )
    val countrySheetState = _countrySheetState.asStateFlow()
}

/** Country Sheet Extension */
object LoginViewModelExt {
    fun LoginViewModel.fetchCountrySheet() {
        viewModelScope.launch {
            val result = networkService.getRequest<List<LoginCountryResDto>>(routeCode = NetworkConstants.FETCH_COUNTRY_JSON)
            _countrySheetState.value = countrySheetState.value.copy(result, true)
        }
    }
    fun LoginViewModel.dismissCountrySheet() {
        _countrySheetState.value = countrySheetState.value.copy(emptyList(),false)
    }
}