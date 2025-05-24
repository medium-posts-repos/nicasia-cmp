package junkeritechnepal.nicasiacmp

import LoginScreen
import androidx.compose.ui.window.ComposeUIViewController
import junkeritechnepal.nicasiacmp.app.navigation.AppNavigationHost

fun MainViewController() = ComposeUIViewController { AppNavigationHost() }