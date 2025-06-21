package junkeritechnepal.nicasiacmp.app.router

import androidx.navigation.NavHostController
import io.ktor.util.Hash
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.core.extensions.withRoute
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import toJsonString

class Router(private val navigator: NavHostController) {
    fun route(itemDto: MenuItemDto) {
//        if(itemDto.subMenus?.isNotEmpty() == true) {
            return routeToSubMenus(itemDto)
//        }
    }

    private fun routeToSubMenus(menuResDto: MenuItemDto) {
        navigator.navigate(NavigationRoutes.SUBMENUS_ROUTE.name.withRoute(menuResDto.toJsonString()))
    }
}