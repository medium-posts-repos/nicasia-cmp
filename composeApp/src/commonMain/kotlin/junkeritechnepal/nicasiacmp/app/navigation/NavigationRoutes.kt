package junkeritechnepal.nicasiacmp.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

val defaultExtrasArgument = listOf(navArgument("extras") { type = NavType.StringType })

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