package junkeritechnepal.nicasiacmp.infrastructure.utils

import android.content.res.Resources

actual fun isIos(): Boolean {
    return false
}

actual fun deviceWidth(): Float {
    val configuration = Resources.getSystem().configuration
    return configuration.screenWidthDp.toFloat()
}