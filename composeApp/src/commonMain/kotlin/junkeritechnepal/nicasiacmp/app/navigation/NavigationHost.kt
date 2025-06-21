package junkeritechnepal.nicasiacmp.app.navigation

import DashboardContainerScreen
import LoginScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import junkeritechnepal.nicasiacmp.app.extensions.getExtras
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.core.extensions.toExtras
import junkeritechnepal.nicasiacmp.modules.designSystem.DesignSystemScreen
import junkeritechnepal.nicasiacmp.modules.login.LoginSecondaryScreen
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import junkeritechnepal.nicasiacmp.modules.menus.MenuViews
import junkeritechnepal.nicasiacmp.modules.sms.SmsScreen

val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavController not provided")
}

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    val router = Router(navController)

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.LOGIN_ROUTE.name,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            composable(NavigationRoutes.DESIGN_SYSTEM.name) { DesignSystemScreen() }
            composable(NavigationRoutes.SMS.name) { SmsScreen(router) }
            setupProtectedRoutes()
            setupNonProtectedRoutes()
            setupMenuRoutes(router)
        }
    }
}

fun NavGraphBuilder.setupMenuRoutes(router: Router) {
    composable(NavigationRoutes.SUBMENUS_ROUTE.name.toExtras(),
        arguments = defaultExtrasArgument
    ) {
        val extras = it.savedStateHandle.getExtras<MenuItemDto>()
        println("extras: ${it.savedStateHandle.getExtras<MenuItemDto>()}")
        MenuViews.MenuCardListScreen(router = router, extras)
    }
}

fun NavGraphBuilder.setupNonProtectedRoutes() {

    composable(NavigationRoutes.LOGIN_ROUTE.name) {
        LoginScreen()
    }

    composable(NavigationRoutes.LOGIN_SECONDARY_ROUTE.name) {
        LoginSecondaryScreen()
    }
}

fun NavGraphBuilder.setupProtectedRoutes() {
    composable(NavigationRoutes.DASHBOARD_ROUTE.name) {
        DashboardContainerScreen()
    }
}

@Composable
fun SplashScreen() {
    Text("Splash Screen")
}