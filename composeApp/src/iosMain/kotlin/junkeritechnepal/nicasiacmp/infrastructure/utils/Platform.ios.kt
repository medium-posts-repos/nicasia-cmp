package junkeritechnepal.nicasiacmp.infrastructure.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
actual fun deviceWidth(): Float {
    return CGRectGetWidth(UIScreen.mainScreen.bounds).toFloat()
}