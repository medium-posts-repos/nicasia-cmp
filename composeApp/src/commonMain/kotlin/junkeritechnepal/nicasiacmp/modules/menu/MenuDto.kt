package junkeritechnepal.nicasiacmp.modules.menu

import kotlinx.serialization.Serializable

@Serializable
data class MenuResDto(
    val success: Boolean? = null,
    val message: String? = null,
    val data: List<MenuItemDto>? = null
)

@Serializable
data class MenuItemDto(
    val name: String? = null,
    val icon: String? = null,
    val code: String? = null,
    val type: String? = null
)