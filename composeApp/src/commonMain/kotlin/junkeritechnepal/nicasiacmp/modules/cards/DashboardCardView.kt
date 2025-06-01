package junkeritechnepal.nicasiacmp.modules.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle

@Composable
fun DashboardCardView() {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.Red)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Column {
                Text("Good Morning", style = AppTextStyle.smallLight(fontWeight = FontWeight.Bold))
                Text("SWORNIM BIKRAM SHAH", style = AppTextStyle.smallLight(fontWeight = FontWeight.Normal))

                Spacer(modifier = Modifier.height(16.dp))
                Text("CHAMATKARIK SHAREHOLDER", style = AppTextStyle.bodyLight(fontWeight = FontWeight.Medium))
                Text("SAVING ACCOUNT", style = AppTextStyle.bodyLight(fontWeight = FontWeight.Medium))
                Text("XXXXXX.XX", style = AppTextStyle.bodyLight(fontWeight = FontWeight.Medium))
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("XXX.XX", style = AppTextStyle.bodyLight(fontWeight = FontWeight.Bold))
                    Icon(Icons.Outlined.AccountBox, contentDescription = "", modifier = Modifier.height(12.dp).width(12.dp))
                }
            }
        }
    }
}