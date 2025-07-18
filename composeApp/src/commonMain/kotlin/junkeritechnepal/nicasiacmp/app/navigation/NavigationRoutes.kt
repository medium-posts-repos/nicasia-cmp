package junkeritechnepal.nicasiacmp.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import kotlinx.serialization.Serializable

val defaultExtrasArgument = listOf(navArgument("menuItemDto") { type = NavType.StringType })

@Serializable
data class RouteExtras(
    val menuItemDto: MenuItemDto? = null
)

@Serializable
enum class NavigationRoutes {
    LOGIN_ROUTE,
    LOGIN_SECONDARY_ROUTE,
    SPLASH_ROUTE,
    WALKTHROUGH_ROUTE,
    REGISTER_ROUTE,
    LOCATION_REQUEST_ROUTE,
    DASHBOARD_ROUTE,
    RIDE_MAP_ROUTE,
    PROFILE_ROUTE,
    DESIGN_SYSTEM,
    SMS,
    SUBMENUS_ROUTE,
    MENU_ROUTE
}

fun NavigationRoutes.toRoute(extras: String? = null): PrivateRouteIntent {
    return PrivateRouteIntent(code = this.name, extras = extras)
}


@Serializable
data class PrivateRouteIntent(val title: String? = null, val code: String? = null, val extras: String? = null)