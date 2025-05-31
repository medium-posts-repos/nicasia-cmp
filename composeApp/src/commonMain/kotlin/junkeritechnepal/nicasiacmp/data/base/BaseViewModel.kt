package junkeritechnepal.nicasiacmp.data.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {
    inline fun CoroutineScope.launchSafe(
        crossinline onError: (Throwable) -> Unit = {},
        crossinline onSuccess: suspend () -> Unit
    ) {
        launch {
            try {
                onSuccess()
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}