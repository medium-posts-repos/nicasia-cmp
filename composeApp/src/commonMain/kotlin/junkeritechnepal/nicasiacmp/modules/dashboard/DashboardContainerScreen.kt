import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.navigation.LocalRouter
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.accounts.AccountPageScreen
import junkeritechnepal.nicasiacmp.modules.camera.CameraScreen
import junkeritechnepal.nicasiacmp.modules.dashboard.HomeScreen1
import junkeritechnepal.nicasiacmp.modules.designSystem.appColorPrimary
import junkeritechnepal.nicasiacmp.modules.profile.ProfileScreenModule.ProfileContainerScreen
import junkeritechnepal.nicasiacmp.modules.sendmoney.SendMoneyContainerScreen

@Composable
fun DashboardContainerScreen() {
    val router = LocalRouter.current

    val pagerState = rememberPagerState(
        initialPage = 1, // Start with middle screen
        pageCount = { 3 }
    )

    // Lift selectedTab and scrollState up so they persist across recompositions

    println("DashboardContainerScreen recomposed with selectedTab: ")

    var selectedTab by remember { mutableIntStateOf(0) }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val screen: @Composable () -> Unit = remember(page) {
            when (page) {
                0 -> { { AccountPageScreen() } }
                1 -> { { DashboardScreen(router, selectedTab) { selectedTab = it } } }
                2 -> { { CameraScreen() } }
                else -> { { Text("Unknown page") } }
            }
        }

        screen() // Call the selected screen composable
    }
}

@Composable
private fun DashboardScreen(
    router: Router,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val finalBottomPadding = bottomPadding + 24.dp

    val items = listOf("Home", "Payments", "", "Transfers", "Profile")

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
                            selected = selectedTab == index,
                            onClick = { onTabSelected(index) },
                            icon = {
                                when(index) {
                                    0 -> { Icon(Icons.Outlined.Home, contentDescription = label) }
                                    1 -> { Icon(Icons.Outlined.ShoppingCart, contentDescription = label) }
                                    2 -> { QRScanNavigationBarItem(index) { onTabSelected(index) } }
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
            when (selectedTab) {
                0 -> { HomeScreen1() }
                3 -> { SendMoneyContainerScreen(router) }
                4 -> { ProfileContainerScreen() }
                else -> { Text("Current tab $selectedTab") }
            }
        }
    }
}

@Composable
private fun QRScanNavigationBarItem(index: Int, onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = appColorPrimary,
        contentColor = Color.White,
        onClick = onClick,
        shape = CircleShape,
        // Optional: Customize FAB appearance
        // containerColor = MaterialTheme.colorScheme.secondaryContainer,
        // contentColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.size(56.dp) // Adjust size as needed
    ) {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "Scan QR"
        )
    }
}