package junkeritechnepal.nicasiacmp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.window.ComposeUIViewController
import junkeritechnepal.nicasiacmp.app.di.KoinInitializer
import junkeritechnepal.nicasiacmp.app.navigation.AppNavigationHost
import platform.UIKit.UIColor
import platform.UIKit.UIViewController

@OptIn(ExperimentalComposeUiApi::class)
val controller = ComposeUIViewController(configure = {
    parallelRendering = true
    KoinInitializer().init()
}) {
    AppNavigationHost()
}

fun MainViewController(): UIViewController {
    controller.parentViewController?.view?.backgroundColor = UIColor.orangeColor
    controller.view.backgroundColor = UIColor.orangeColor
    return controller
}