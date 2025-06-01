import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.modules.cards.DashboardCardView
import junkeritechnepal.nicasiacmp.modules.menu.MenuSingleGridView
import junkeritechnepal.nicasiacmp.modules.menu.MenuViewModel

@Composable
fun DashboardContainerScreen() {
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
        when (page) {
            0 -> PageScreen("Left Screen", Color(0xFFBBDEFB))
            1 -> DashboardScreen()
            2 -> PageScreen("Right Screen", Color(0xFFFFCDD2))
        }
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
private fun DashboardScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val menuViewModel by lazy { MenuViewModel() }


    val items = listOf("Home", "Search", "Add", "Profile", "More")

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(Icons.Default.Settings, contentDescription = label)
                        },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when(selectedTab) {
            0 -> { HomeScreen(menuViewModel) }
            else -> { Text("Current tab $selectedTab") }
        }
    }
}

@Composable
private fun HomeScreen(menuViewModel: MenuViewModel) {
    val menuApiRes = menuViewModel.menuApiResState.collectAsState()

    LaunchedEffect(Unit) {
        menuViewModel.fetchPublicMenus()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
        DashboardCardView()
        MenuSingleGridView(data = menuApiRes.value)
    }
}