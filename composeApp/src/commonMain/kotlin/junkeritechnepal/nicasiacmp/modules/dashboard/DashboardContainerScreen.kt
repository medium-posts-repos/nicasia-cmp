import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.camera.CameraScreen
import junkeritechnepal.nicasiacmp.modules.dashboard.HomeScreen
import junkeritechnepal.nicasiacmp.modules.designSystem.appColorPrimary
import junkeritechnepal.nicasiacmp.modules.menus.MenuViewModel
import junkeritechnepal.nicasiacmp.modules.sendmoney.SendMoneyContainerScreen
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardContainerScreen(router: Router) {
    val pagerState = rememberPagerState(
        initialPage = 1, // Start with middle screen
        pageCount = { 3 }
    )

    // Lift selectedTab and scrollState up so they persist across recompositions

    println("DashboardContainerScreen recomposed with selectedTab: ")

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val screen: @Composable () -> Unit = remember(page) {
            when (page) {
                0 -> { { PageScreen("Left Screen", Color(0xFFBBDEFB)) } }
                1 -> { { DashboardScreen(router) } }
                2 -> { { CameraScreen() } }
                else -> { { Text("Unknown pagee") } }
            }
        }

        screen() // Call the selected screen composable
    }
}

@Composable
private fun PageScreen(label: String, color: Color) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .background(Color.Yellow)
        .verticalScroll(scrollState)
        .fillMaxWidth()
    ) {
        for (i in 1..200) {
            Text(text = label, fontSize = 24.sp, color = Color.Black)
        }
    }
}

@Composable
private fun DashboardScreen(
    router: Router
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val finalBottomPadding = bottomPadding + 24.dp
    var selectedTab by remember { mutableIntStateOf(0) }

    val items = listOf("Home", "Search", "", "Profile", "More")

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
                            onClick = { selectedTab = index },
                            icon = {
                                when(index) {
                                    0 -> { Icon(Icons.Default.Home, contentDescription = label) }
                                    1 -> {Icon(Icons.Default.ShoppingCart, contentDescription = label) }
                                    2 -> { QRScanNavigationBarItem(index) { selectedTab = index } }
                                    3 -> {Icon(Icons.Default.Person, contentDescription = label) }
                                    4 -> {Icon(Icons.Default.Person, contentDescription = label) }
                                }
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Crossfade(targetState = selectedTab, animationSpec = tween(durationMillis = 400)) { currentTab ->
                when (currentTab) {
                    0 -> { HomeScreen() }
                    3 -> { SendMoneyContainerScreen(router) }
                    else -> { Text("Current tab $currentTab") }
                }
            }
        }

        Image(
            painter = painterResource(Res.drawable.nicasisa),
            contentDescription = "Floating Image",
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .padding(
                    bottom  = finalBottomPadding,
                    end = 24.dp
                )
        )
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
            Icons.Filled.Call,
            contentDescription = "Scan QR"
        )
    }
}