package junkeritechnepal.nicasiacmp.modules.menus

import androidx.lifecycle.viewModelScope
import junkeritechnepal.nicasiacmp.data.base.BaseViewModel
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkConstants
import junkeritechnepal.nicasiacmp.infrastructure.network.NetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MenuViewModel: BaseViewModel() {
    private val networkService by lazy { NetworkService.INSTANCE }

    init {
//        fetchPublicMenus()
    }

    private val _menuApiResState = MutableStateFlow(MenuResDto())
    val menuApiResState = _menuApiResState.asStateFlow()

    fun fetchPublicMenus() {

        _menuApiResState.value.apply {
            if (data?.isNotEmpty() == true ) {
                _menuApiResState.value = this
                return
            }
        }

        println("Going to fetchPublicMenus...")

        viewModelScope.launchSafe {
//            val result = networkService.getRequest<MenuResDto>(NetworkConstants.PUBLIC_MENUS)
            val result = menuMockData
            _menuApiResState.value = result
            println(result)
        }
    }
}