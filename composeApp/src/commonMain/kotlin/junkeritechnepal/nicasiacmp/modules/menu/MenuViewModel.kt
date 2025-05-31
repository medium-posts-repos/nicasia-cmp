package junkeritechnepal.nicasiacmp.modules.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import junkeritechnepal.nicasiacmp.data.GenericUIState
import junkeritechnepal.nicasiacmp.data.base.BaseViewModel
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkConstants
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkService
import junkeritechnepal.nicasiacmp.modules.login.LoginCountryResDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MenuViewModel: BaseViewModel() {
    private val networkService by lazy { NetworkService.INSTANCE }

    private val _menuApiResState = MutableStateFlow(MenuResDto())
    val menuApiResState = _menuApiResState.asStateFlow()

    fun fetchPublicMenus() {
        viewModelScope.launchSafe {
            val result = networkService.getRequest<MenuResDto>(NetworkConstants.PUBLIC_MENUS)
            _menuApiResState.value = result
            println(result)
        }
    }
}