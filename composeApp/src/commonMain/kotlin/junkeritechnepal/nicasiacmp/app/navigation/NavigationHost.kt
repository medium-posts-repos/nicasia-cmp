package junkeritechnepal.nicasiacmp.app.navigation

import DashboardContainerScreen
import FormViewModel
import LoginScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import junkeritechnepal.nicasiacmp.app.di.koinViewModel
import junkeritechnepal.nicasiacmp.app.extensions.getExtras
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.app.viewmodels.ShareViewModel
import junkeritechnepal.nicasiacmp.core.extensions.receiveExtras
import junkeritechnepal.nicasiacmp.modules.designSystem.DesignSystemScreen
import junkeritechnepal.nicasiacmp.modules.forms.DynamicFormScreen
import junkeritechnepal.nicasiacmp.modules.login.LoginSecondaryScreen
import junkeritechnepal.nicasiacmp.modules.menus.MenuItemDto
import junkeritechnepal.nicasiacmp.modules.menus.MenuViews
import junkeritechnepal.nicasiacmp.modules.sms.SmsScreen
import kotlinx.serialization.Serializable
import toJsonString
import toObject

val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavController not provided")
}

val LocalRouter = compositionLocalOf<Router> {
    error("Router not provided")
}

@Composable
fun AppNavigationHost() {
    val navController = rememberNavController()
    val router = remember { Router(navController) }

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalRouter provides router
    ) {
        NavHost(
            navController = navController,
            startDestination = PrivateRouteIntent(code = NavigationRoutes.LOGIN_ROUTE.name),
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
//            enterTransition = {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Left,
//                    animationSpec = tween(300)
//                )
//            },
//            exitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Left,
//                    animationSpec = tween(300)
//                )
//            },
//            popEnterTransition = {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Right,
//                    animationSpec = tween(300)
//                )
//            },
//            popExitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Right,
//                    animationSpec = tween(300)
//                )
//            }
        ) {
            setupNonProtectedRoutes()
        }
    }
}

fun NavGraphBuilder.setupNonProtectedRoutes() {
    composable<PrivateRouteIntent> {
        val intent = it.toRoute<PrivateRouteIntent>()
        when(intent.code) {
            NavigationRoutes.LOGIN_ROUTE.name -> {
                LoginScreen()
            }
            NavigationRoutes.LOGIN_SECONDARY_ROUTE.name -> {
                LoginSecondaryScreen()
            }
            NavigationRoutes.DASHBOARD_ROUTE.name -> {
                DashboardContainerScreen()
            }
            NavigationRoutes.DESIGN_SYSTEM.name -> {
                DesignSystemScreen()
            }
            NavigationRoutes.SMS.name -> {
                SmsScreen()
            }
            NavigationRoutes.SUBMENUS_ROUTE.name -> {
                MenuViews.MenuCardListScreen(intent)
            }
            NavigationRoutes.MENU_ROUTE.name -> {
                DynamicFormScreen(FormViewModel(), intent)
            }
            else -> {
                Text("No routes configured")
            }
        }
    }
}


@Composable
fun SplashScreen() {
    Text("Splash Screen")
}