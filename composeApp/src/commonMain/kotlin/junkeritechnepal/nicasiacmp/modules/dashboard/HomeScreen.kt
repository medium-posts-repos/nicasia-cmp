package junkeritechnepal.nicasiacmp.modules.dashboard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.modules.cards.DashboardCardView
import junkeritechnepal.nicasiacmp.modules.menus.MenuResDto
import junkeritechnepal.nicasiacmp.modules.menus.MenuSingleGridView
import junkeritechnepal.nicasiacmp.modules.menus.MenuViewModel
import junkeritechnepal.nicasiacmp.modules.products.ProductViewModule

@Composable
fun HomeScreen() {
    val menuViewModel by remember { mutableStateOf(MenuViewModel()) }
    val menuApiRes by menuViewModel.menuApiResState.collectAsState()
    var cachedMenu by remember { mutableStateOf(MenuResDto()) }
    val scrollState = rememberScrollState()

    // Fetch data once
    LaunchedEffect(Unit) {
        menuViewModel.fetchPublicMenus()
    }

    // Cache data after successful fetch
    LaunchedEffect(menuApiRes.data) {
        if (menuApiRes.success == true) {
            cachedMenu = menuApiRes
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        DashboardCardView()
        MenuSingleGridView(title = "Financial Services", data = cachedMenu)
        ProductViewModule.HorizontalPhotoScroller()
        MenuSingleGridView(title = "Payments", data = cachedMenu)
        MenuSingleGridView(title = "FonePayments", data = cachedMenu)
        MenuSingleGridView(title = "ECommerces", data = cachedMenu)

        for (i in 1..200) {
            Text(text = "Random number $i", fontSize = 24.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}