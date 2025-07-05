package junkeritechnepal.nicasiacmp.modules.menus

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
    val desc: String? = null,
    val icon: String? = null,
    val code: String? = null,
    val type: String? = null,
    var subMenus: List<MenuItemDto> = listOf()
)

object MenuDataSource {
    val paymentSubMenus: List<MenuItemDto>
        get() = listOf(
            MenuItemDto("Recharge Cards", desc = "Purchase Various Recharge Cards", code = "RC"),
            MenuItemDto("NT Topup", desc = "Purchase Various Recharge Cards", code = "NTCTOP"),
            MenuItemDto("Ncell Topup","Ncell Topup", null, code = "NCELLTOP"),
            MenuItemDto("Load eSewa","Transfer to eSewa A/C", null, code = "LOADESEWA")
        )
}
