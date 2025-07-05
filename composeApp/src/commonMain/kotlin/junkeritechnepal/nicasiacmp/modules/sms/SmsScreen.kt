package junkeritechnepal.nicasiacmp.modules.sms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import junkeritechnepal.nicasiacmp.app.navigation.LocalNavController
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.app.navigation.toRoute
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.backgroundColor
import junkeritechnepal.nicasiacmp.modules.menus.MenuDataSource
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.bank_logo
import nicasia_cmp.composeapp.generated.resources.card_design
import nicasia_cmp.composeapp.generated.resources.compose_multiplatform
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource
import toJsonString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsScreen(router: Router) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val items = listOf(
        Triple("General Inquiry", Res.drawable.nicasisa, MenuDataSource.paymentSubMenus),
        Triple("Send Money", Res.drawable.card_design, MenuDataSource.paymentSubMenus),
        Triple("Payments", Res.drawable.compose_multiplatform, MenuDataSource.paymentSubMenus),
        Triple("Settings", Res.drawable.nicasisa, MenuDataSource.paymentSubMenus)
    )

    Scaffold(
        topBar = {
            NavHeaderView(scrollBehavior = scrollBehavior)
        },
        containerColor = backgroundColor,
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { (title, iconRes, items) ->
                Card(
                    modifier = Modifier
                        .clickable {
                            val intent = MenuDataSource.paymentSubMenus.first()
                            intent.subMenus = items
                            router.route(NavigationRoutes.SUBMENUS_ROUTE.toRoute(extras = intent.toJsonString()))
                        }
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(iconRes),
                            contentDescription = title,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(title, style = AppTextStyle.smallNormalPrimary())
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavHeaderView(scrollBehavior: TopAppBarScrollBehavior) {
    val navController = LocalNavController.current

    Column {
        TopAppBar(
            title = {
                // Center image using Box
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.bank_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.height(80.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack(
                        NavigationRoutes.LOGIN_ROUTE.toRoute(),
                        inclusive = false,
                        saveState = false
                    )
                }) {
                    Icon(Icons.Outlined.Home, contentDescription = "Home")
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(NavigationRoutes.DESIGN_SYSTEM.toRoute())
                }) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Language")
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.White),
            scrollBehavior = scrollBehavior
        )
    }

}
