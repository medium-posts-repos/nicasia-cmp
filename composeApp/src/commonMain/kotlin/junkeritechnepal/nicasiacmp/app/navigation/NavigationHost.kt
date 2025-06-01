package junkeritechnepal.nicasiacmp.app.navigation

import DashboardContainerScreen
import LoginScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import junkeritechnepal.nicasiacmp.modules.designSystem.DesignSystemScreen
import junkeritechnepal.nicasiacmp.modules.login.LoginSecondaryScreen

val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavController not provided")
}

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.LOGIN_ROUTE.name,
            modifier = Modifier
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
            composable(NavigationRoutes.DESIGN_SYSTEM.name) {
                DesignSystemScreen()
            }

            composable(NavigationRoutes.LOGIN_ROUTE.name) {
                LoginScreen()
            }

            composable(NavigationRoutes.LOGIN_SECONDARY_ROUTE.name) {
                LoginSecondaryScreen()
            }

            composable(NavigationRoutes.DASHBOARD_ROUTE.name) {
                DashboardContainerScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Text("Splash Screen")
}