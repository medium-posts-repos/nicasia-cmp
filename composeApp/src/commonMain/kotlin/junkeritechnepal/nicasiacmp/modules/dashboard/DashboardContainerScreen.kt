
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.accounts.AccountPageScreen
import junkeritechnepal.nicasiacmp.modules.camera.CameraScreen
import junkeritechnepal.nicasiacmp.modules.dashboard.HomeScreen1
import junkeritechnepal.nicasiacmp.modules.designSystem.appColorPrimary
import junkeritechnepal.nicasiacmp.modules.profile.ProfileScreenModule.ProfileContainerScreen
import junkeritechnepal.nicasiacmp.modules.sendmoney.SendMoneyContainerScreen
import kotlinx.coroutines.launch

@Composable
fun DashboardContainerScreen(router: Router) {

    val pagerState = rememberPagerState(
        initialPage = 1, // Start with middle screen
        pageCount = { 3 }
    )

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> AccountPageScreen(router)
            1 -> DashboardScreen(router)
            2 -> CameraScreen()
            else -> Text("Unknown page")
        }
    }
}

@Composable
private fun DashboardScreen(
    router: Router
) {
    val items = listOf("Home", "Payments", "", "Transfers", "Profile")

    val pagerState = rememberPagerState(pageCount = { items.size })
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().padding(
        WindowInsets.systemBars.only(
            WindowInsetsSides.Top + WindowInsetsSides.Horizontal
        ).asPaddingValues()
    )) {
        Scaffold(
            containerColor = Color(0xfffafafa),
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    items.forEachIndexed { index, label ->

                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            icon = {
                                when(index) {
                                    0 -> { Icon(Icons.Outlined.Home, contentDescription = label) }
                                    1 -> { Icon(Icons.Outlined.ShoppingCart, contentDescription = label) }
                                    2 -> { QRScanNavigationBarItem {
                                        scope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    } }
                                    3 -> {Icon(Icons.Outlined.Send, contentDescription = label) }
                                    4 -> {Icon(Icons.Outlined.Person, contentDescription = label) }
                                }
                            },
                            label = { Text(label, fontSize = 12.sp) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            HorizontalPager(state = pagerState, modifier = Modifier.padding(innerPadding)) { page ->
                when (page) {
                    0 -> { HomeScreen1(router, scrollState) }
                    3 -> { SendMoneyContainerScreen(router) }
                    4 -> { ProfileContainerScreen() }
                    else -> { Text("Current tab $page") }
                }
            }
        }
    }
}

@Composable
private fun QRScanNavigationBarItem(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = appColorPrimary,
        contentColor = Color.White,
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(56.dp) // Adjust size as needed
    ) {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "Scan QR"
        )
    }
}
