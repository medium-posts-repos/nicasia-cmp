package junkeritechnepal.nicasiacmp.modules.accounts.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.card_design
import org.jetbrains.compose.resources.painterResource

object AccountView {
    @Composable
    fun AccountImageDescView() {
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(Res.drawable.card_design), // put your image in resources
                contentDescription = "Sample Image",
                modifier = Modifier
                    .requiredSize(width = 46.dp, height = 46.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Swornim bikram shah", style = AppTextStyle.bodyNormalDark)
                Row(modifier = Modifier.align(Alignment.Start), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "",
                        modifier = Modifier.requiredSize(width = 16.dp, height = 16.dp))
                    Text("982213232132")
                }
            }
        }
    }
}