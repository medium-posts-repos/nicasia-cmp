import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.modules.camera.CameraScreen
import junkeritechnepal.nicasiacmp.modules.cards.DashboardCardView
import junkeritechnepal.nicasiacmp.modules.menu.MenuSingleGridView
import junkeritechnepal.nicasiacmp.modules.menu.MenuViewModel
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardContainerScreen() {
    val menuViewModel by lazy { MenuViewModel() }

    val pagerState = rememberPagerState(
        initialPage = 1, // Start with middle screen
        pageCount = { 3 }
    )

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        modifier = Modifier.fillMaxSize()
            .padding(
                WindowInsets.systemBars.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                ).asPaddingValues()
            )
    ) { page ->
        val screen: @Composable () -> Unit = remember(page) {
            when (page) {
                0 -> { { PageScreen("Left Screen", Color(0xFFBBDEFB)) } }
                1 -> { { DashboardScreen(menuViewModel) } }
                2 -> { { CameraScreen() } }
                else -> { { Text("Unknown pagee") } }
            }
        }

        screen() // Call the selected screen composable
    }
}

@Composable
private fun PageScreen(label: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, fontSize = 24.sp, color = Color.Black)
    }
}

@Composable
private fun DashboardScreen(menuViewModel: MenuViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val finalBottomPadding = bottomPadding + 24.dp

    val items = listOf("Home", "Search", "Profile", "More")

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color(0xfffafafa),
            bottomBar = {
                NavigationBar(containerColor = Color(0xfffafafa)) {
                    items.forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                when(index) {
                                    0 -> { Icon(Icons.Default.Home, contentDescription = label) }
                                    1 -> {Icon(Icons.Default.ShoppingCart, contentDescription = label) }
                                    2 -> {Icon(Icons.Default.Send, contentDescription = label) }
                                    3 -> {Icon(Icons.Default.Person, contentDescription = label) }
                                }
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { innerPadding ->

            val screen: @Composable () -> Unit = remember(selectedTab) {
                when (selectedTab) {
                    0 -> { { HomeScreen(menuViewModel, scrollState) } }
                    else -> { { Text("Current tab $selectedTab") } }
                }
            }
            screen()
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
private fun HomeScreen(menuViewModel: MenuViewModel, scrollState: ScrollState) {
    val menuApiRes = menuViewModel.menuApiResState.collectAsState()

    LaunchedEffect(Unit) {
        menuViewModel.fetchPublicMenus()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        DashboardCardView()
        MenuSingleGridView(title = "Financial Services", data = menuApiRes.value)
        MenuSingleGridView(title = "Payments", data = menuApiRes.value)
        MenuSingleGridView(title = "FonePayments", data = menuApiRes.value)
        MenuSingleGridView(title = "ECommerces", data = menuApiRes.value)
        Spacer(modifier = Modifier.height(100.dp))
    }
}