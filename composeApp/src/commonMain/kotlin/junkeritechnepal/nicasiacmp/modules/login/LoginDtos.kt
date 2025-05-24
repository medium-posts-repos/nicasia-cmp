package junkeritechnepal.nicasiacmp.modules.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginCountryResDto(
    val name: String?,
    val code: String?,
    val emoji: String?
)