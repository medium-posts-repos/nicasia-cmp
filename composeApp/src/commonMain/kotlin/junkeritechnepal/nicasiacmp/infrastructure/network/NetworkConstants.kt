package junkeritechnepal.nicasiacmp.infrastructure.network


class NetworkConstants  {

    companion object {
        private const val BASE_URL = "http://192.168.1.4:9000/api/v1"
        const val FETCH_COUNTRY_JSON = "FETCH_COUNTRY_JSON"
        const val PUBLIC_MENUS = "PUBLIC_MENUS"
        const val SEND_MONEY_MENUS = "PUBLIC_MENUS"

        fun routeFor(code: String): String {
            return when(code) {
                FETCH_COUNTRY_JSON -> { "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/index.json" }
                PUBLIC_MENUS -> { "${BASE_URL}/public/menus" }
                else -> { "" }
            }
        }
    }
}