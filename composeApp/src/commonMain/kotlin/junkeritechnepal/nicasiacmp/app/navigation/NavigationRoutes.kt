package junkeritechnepal.nicasiacmp.app.navigation

import kotlinx.serialization.Serializable

@Serializable
enum class NavigationRoutes {
    LOGIN_ROUTE,
    SPLASH_ROUTE,
    WALKTHROUGH_ROUTE,
    REGISTER_ROUTE,
    LOCATION_REQUEST_ROUTE,
    DASHBOARD_ROUTE,
    RIDE_MAP_ROUTE,
    PROFILE_ROUTE
}