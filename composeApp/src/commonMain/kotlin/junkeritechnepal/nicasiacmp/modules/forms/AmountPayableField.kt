package junkeritechnepal.nicasiacmp.modules.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.appColorPrimary
import junkeritechnepal.nicasiacmp.modules.designSystem.primary200
import junkeritechnepal.nicasiacmp.modules.designSystem.primary300
import junkeritechnepal.nicasiacmp.modules.designSystem.textColorPrimary

val myAmountChips = listOf(
    AmountPayableOption(amount = "€2.50"),
    AmountPayableOption(amount = "€5.00", label = "Coffee"),
    AmountPayableOption(amount = "€10.00"),
    AmountPayableOption(amount = "€12.75", label = "Lunch")
    // Add more chips as needed
)


@Composable
private fun AmountChip(
    amountData: AmountPayableOption,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the chip
            .background(primary200) // Background color
            .border(
                1.dp,
                primary300,
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amountData.label?.let { "${amountData.amount} - $it" } ?: amountData.amount,
            style = AppTextStyle.bodyNormalDark
        )}
}

@Composable
fun AmountChipFormField(
   formField: FormField
) {
    var isFocused by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(formField.label, style = AppTextStyle.textNormalDim())
        BasicTextField(
            value = formField.value,
            onValueChange = {  },
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, if (isFocused) textColorPrimary else Color.LightGray,
                    RoundedCornerShape(10.dp))
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(horizontal = 10.dp, vertical = 12.dp)
                ) {
                    if (formField.value.isEmpty()) {
                        Text(formField.placeHolder ?: "", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), // Padding around the row
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between chips
        ) {
            items(myAmountChips) { chipData ->
                AmountChip(amountData = chipData)
            }
        }
    }
}