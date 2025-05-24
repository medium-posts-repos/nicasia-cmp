package junkeritechnepal.nicasiacmp.infrastructure.network


class NetworkConstants  {

    companion object {
        public const val FETCH_COUNTRY_JSON = "FETCH_COUNTRY_JSON"

        public fun routeFor(code: String): String {
            return when(code) {
                FETCH_COUNTRY_JSON -> { "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/index.json" }
                else -> { "" }
            }
        }
    }
}