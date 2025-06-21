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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.router.Router
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource

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

    @Composable
    fun MenuCardListScreen(router: Router, extras: MenuItemDto?) {
        val dataSource = extras?.subMenus ?: listOf()
        LazyColumn(modifier = Modifier) {
            items(dataSource.count()) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).clickable {  }
                    ) {
                        Text(
                            text = dataSource[item].name ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = dataSource[item].desc ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}