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

    private val _isLoading = MutableStateFlow(false)
    internal val isLoading = _isLoading.asStateFlow()

    fun executeLogin() {
        _isLoading.value = true

//        viewModelScope.launch {
//            println("Before delay")
//            delay(5000)
//            println("After delay")
//            _isLoading.value = false
//        }
    }
}

/** Country Sheet Extension */
object LoginViewModelExt {
    fun LoginViewModel.fetchCountrySheet() {
        viewModelScope.launch {
            if (countrySheetState.value.data.isNotEmpty()) {
                _countrySheetState.value = countrySheetState.value
                return@launch
            }

            val result = networkService.getRequest<List<LoginCountryResDto>>(routeCode = NetworkConstants.FETCH_COUNTRY_JSON)
            _countrySheetState.value = countrySheetState.value.copy(data = result, isVisible = true)
        }
    }
    fun LoginViewModel.dismissCountrySheet() {
        _countrySheetState.value = countrySheetState.value.copy(
            data = emptyList(),
            isVisible = false
        )
    }
}