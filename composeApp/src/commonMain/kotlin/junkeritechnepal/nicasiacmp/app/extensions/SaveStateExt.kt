package junkeritechnepal.nicasiacmp.app.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getExtras(key: String = "extras"): T? = get<T>(key)