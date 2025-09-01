package junkeritechnepal.nicasiacmp.modules.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun AccountPageScreen() {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxWidth()
    ) {
        for (i in 1..200) {
            Text(text = "My accounts related operations", fontSize = 24.sp, color = Color.Black)
        }
    }
}