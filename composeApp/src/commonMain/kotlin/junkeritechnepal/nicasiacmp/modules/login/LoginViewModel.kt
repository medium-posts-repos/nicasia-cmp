package junkeritechnepal.nicasiacmp.modules.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkConstants
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class LoginViewModel: ViewModel() {

    private val networkService by lazy { NetworkService.INSTANCE }

    var showCountrySheet by mutableStateOf(false)

    fun fetchCountrySheet() {
        viewModelScope.launch {
            val result = networkService.getRequest<List<LoginCountryResDto>>(routeCode = NetworkConstants.FETCH_COUNTRY_JSON)
            showCountrySheet = true
            println("fetchCountrySheet -> $result")
        }
    }
}