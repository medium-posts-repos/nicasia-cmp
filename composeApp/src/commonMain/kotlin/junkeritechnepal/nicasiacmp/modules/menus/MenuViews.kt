package junkeritechnepal.nicasiacmp.modules.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.app.navigation.PrivateRouteIntent
import junkeritechnepal.nicasiacmp.app.navigation.toRoute
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.backgroundColor
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource
import toJsonString
import toObject

object MenuViews {
    @Composable
    fun IconTitleDescCardView() {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Start), modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(Res.drawable.nicasisa),
                    contentDescription = "",
                    modifier = Modifier.size(26.dp)
                )
                Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("Open a Lite Account", style = AppTextStyle.boldDark(fontSize = 12.sp))
                    Text("Access our services in just few steps", style = AppTextStyle.textNormalDim())
                }
                Spacer(Modifier.weight(1f))
                Image(
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.KeyboardArrowRight),
                    contentDescription = "",
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MenuCardListScreen(router: Router, intent: PrivateRouteIntent?) {
        Scaffold { padding ->
            val dataSource = intent?.extras?.toObject<MenuItemDto>()?.subMenus ?: listOf()

            if(dataSource.isEmpty()) {
                return@Scaffold Text("No data found")
            }

            LazyColumn(modifier = Modifier.padding(padding)) {
                items(dataSource.count()) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).clickable {
                                router.route(NavigationRoutes.MENU_ROUTE.toRoute())
                            }
                        ) {
                            Text(
                                text = dataSource[item].name ?: "",
                                style = AppTextStyle.boldDark()
                            )
                            Text(
                                text = dataSource[item].desc ?: "",
                                style =AppTextStyle.bodyNormalDark,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}