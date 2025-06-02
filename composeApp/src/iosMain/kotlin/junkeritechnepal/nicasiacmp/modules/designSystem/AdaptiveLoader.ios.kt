package junkeritechnepal.nicasiacmp.modules.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIActivityIndicatorViewStyleLarge
import platform.UIKit.UIActivityIndicatorView
import platform.UIKit.UIActivityIndicatorViewStyleGray
import platform.UIKit.UIActivityIndicatorViewStyleWhite
import platform.UIKit.UIActivityIndicatorViewStyleWhiteLarge
import platform.UIKit.UIButtonConfigurationCornerStyleSmall
import platform.UIKit.UIColor
import platform.UIKit.*

//@Composable
//actual fun AdaptiveLoader() {
//    UIKitView(
//        modifier = Modifier.fillMaxSize(),
//        factory = {
//            val indicator = UIActivityIndicatorView(UIActivityIndicatorViewStyleWhite)
//            indicator.setBackgroundColor(UIColor.clearColor)
//            indicator.color = UIColor.grayColor
//            indicator.startAnimating()
//            return@UIKitView indicator
//    }, update = {
//        it.backgroundColor = UIColor.clearColor
//
//    })
//}

@Composable
actual fun AdaptiveLoader() {
    UIKitView(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .background(Color.Transparent),
        factory = {
             ProgressView()
        },
        update = { view ->
            view.backgroundColor = UIColor.clearColor
        }
    )
}

private fun ProgressView(): UIView {
    val progressView = UIProgressView()
    progressView.backgroundColor = UIColor.clearColor
    progressView.progressViewStyle = UIProgressViewStyle.UIProgressViewStyleDefault
    return progressView
}