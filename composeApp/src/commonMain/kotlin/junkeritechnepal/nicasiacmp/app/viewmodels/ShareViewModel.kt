package junkeritechnepal.nicasiacmp.app.viewmodels

import androidx.lifecycle.ViewModel
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class ShareViewModel : ViewModel(), KoinComponent {

    private val _selectedMenuItem = MutableStateFlow<MenuItemDto?>(null)
    val selectedMenuItem: StateFlow<MenuItemDto?> = _selectedMenuItem.asStateFlow()

    fun setMenuItem(item: MenuItemDto) {
        _selectedMenuItem.value = item
    }

    fun clear() {
        _selectedMenuItem.value = null
    }
}