package junkeritechnepal.nicasiacmp.app.router

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.ktor.util.Hash
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.app.viewmodels.ShareViewModel
import junkeritechnepal.nicasiacmp.core.extensions.withExtras
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import toJsonString

class Router(private val navigator: NavHostController) {
    fun route(itemDto: MenuItemDto) {
//        if(itemDto.subMenus?.isNotEmpty() == true) {
            return routeToSubMenus(itemDto)
//        }
    }

    private fun routeToSubMenus(menuResDto: MenuItemDto) {
        navigator.navigate(NavigationRoutes.SUBMENUS_ROUTE.name)
    }
}