package junkeritechnepal.nicasiacmp.modules.designSystem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.TextUnit
import org.jetbrains.compose.ui.tooling.preview.Preview

val textColorDark = Color(0xFF000000)
val textColorMedium = Color(0xFF666666)
val textColorPrimary = Color.Red
val backgroundColor = Color(0xfffafafa)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = textColorDark
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = textColorDark
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = textColorDark
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = textColorMedium
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = textColorMedium
    )
)

object AppTextStyle {
    val bodyNormalDark = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = textColorDark
    )

    fun bodyLight(fontWeight: FontWeight = FontWeight.Medium): TextStyle {
        return TextStyle(
            fontSize = 14.sp,
            fontWeight = fontWeight,
            color = Color.White
        )
    }
    fun largeDark(fontWeight: FontWeight = FontWeight.Bold): TextStyle {
        return TextStyle(
            fontSize = 14.sp,
            fontWeight = fontWeight,
            color = textColorDark
        )
    }
    fun smallLight(fontWeight: FontWeight = FontWeight.Bold): TextStyle {
        return TextStyle(
            fontSize = 12.sp,
            fontWeight = fontWeight,
            color = Color.White
        )
    }
    fun boldLight(fontSize: TextUnit = 16.sp): TextStyle {
        return TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
    fun boldDark(fontSize: TextUnit = 14.sp): TextStyle {
        return TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = textColorDark
        )
    }
    fun textNormalDim(fontSize: TextUnit = 12.sp, weight: FontWeight = FontWeight.Normal): TextStyle {
        return TextStyle(
            fontSize = fontSize,
            fontWeight = weight,
            color = textColorMedium
        )
    }
    fun smallNormalPrimary(fontSize: TextUnit = 12.sp, weight: FontWeight = FontWeight.Normal): TextStyle {
        return TextStyle(
            fontSize = fontSize,
            fontWeight = weight,
            color = textColorPrimary
        )
    }
}

@Preview
@Composable
fun DesignSystemScreen() {
    Scaffold {
        Column {
            Text("Title Large", style = AppTypography.titleLarge)
            Text("Title Medium", style = AppTypography.titleMedium)
            Text("Body Large", style = AppTypography.bodyLarge)
            Text("Body Medium", style = AppTypography.bodyMedium)
            Text("Label Small", style = AppTypography.labelSmall)
            Text("Label Small Dark", style = AppTypography.labelSmall.copy(color = textColorDark))
        }
    }
}