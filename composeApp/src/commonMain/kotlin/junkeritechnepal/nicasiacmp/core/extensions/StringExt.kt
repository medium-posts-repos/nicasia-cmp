package junkeritechnepal.nicasiacmp.core.extensions

fun String.sendExtras(extra: String): String {
    return "$this/${extra}"
}

fun String.receiveExtras(): String {
    return "$this/{extras}"
}