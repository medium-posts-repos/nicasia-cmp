package junkeritechnepal.nicasiacmp.core.extensions

fun String.withExtras(extra: String): String {
    return "$this/${extra}"
}

fun String.toExtras(): String {
    return "$this/{extras}"
}