package junkeritechnepal.nicasiacmp.app.router

import androidx.navigation.NavHostController
import junkeritechnepal.nicasiacmp.app.navigation.PrivateRouteIntent

class Router(private val navigator: NavHostController) {
    fun route(intent: PrivateRouteIntent) {
        navigator.navigate(intent)
    }
}