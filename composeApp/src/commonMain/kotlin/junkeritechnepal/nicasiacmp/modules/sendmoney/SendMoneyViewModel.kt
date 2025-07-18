package junkeritechnepal.nicasiacmp.modules.sendmoney

import junkeritechnepal.nicasiacmp.data.base.BaseViewModel
import junkeritechnepal.nicasiacmp.modules.menus.MenuDataSource
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import junkeritechnepal.nicasiacmp.modules.menus.MenuResDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SendMoneyViewModel: BaseViewModel() {
    private val _menuApiResState = MutableStateFlow(listOf<MenuItemDto>())
    val menuApiResState = _menuApiResState.asStateFlow()

    fun fetchSendMoneyMenus() {
        _menuApiResState.value = MenuDataSource.sendMoneyMenus
    }
}