package junkeritechnepal.nicasiacmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import junkeritechnepal.nicasiacmp.app.navigation.AppNavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigationHost()
    }
}