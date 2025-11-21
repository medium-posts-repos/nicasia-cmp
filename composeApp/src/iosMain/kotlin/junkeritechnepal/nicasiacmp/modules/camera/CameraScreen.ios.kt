package junkeritechnepal.nicasiacmp.modules.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import junkeritechnepal.nicasiacmp.LocalNativeViewProvider

@Composable
actual fun CameraScreen() {
    val localViewFactory = LocalNativeViewProvider.current
    UIKitViewController(
        modifier = Modifier.fillMaxSize(),
        factory = {
        localViewFactory.provideCameraScreen()
    })
}