package junkeritechnepal.nicasiacmp

import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.window.ComposeUIViewController
import junkeritechnepal.nicasiacmp.app.di.KoinInitializer
import junkeritechnepal.nicasiacmp.app.di.factory.NativeViewFactory
import junkeritechnepal.nicasiacmp.app.navigation.AppNavigationHost
import platform.UIKit.UIColor
import platform.UIKit.UIViewController


val LocalNativeViewProvider = staticCompositionLocalOf<NativeViewFactory> {
    error("NativeViewFactory not provided")
}

@OptIn(ExperimentalComposeUiApi::class)
fun MainViewController(nativeViewFactory: NativeViewFactory) = ComposeUIViewController(configure = {
    parallelRendering = true
    KoinInitializer().init()
}) {
    CompositionLocalProvider(LocalNativeViewProvider provides nativeViewFactory) {
        AppNavigationHost()
    }
}