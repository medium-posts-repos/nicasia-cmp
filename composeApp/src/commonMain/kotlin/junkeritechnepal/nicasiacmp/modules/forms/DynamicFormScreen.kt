package junkeritechnepal.nicasiacmp.modules.forms

import FormViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import junkeritechnepal.nicasiacmp.app.navigation.LocalNavController
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.backgroundColor
import junkeritechnepal.nicasiacmp.modules.designSystem.textColorPrimary
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DynamicFormScreen(formViewModel: FormViewModel = viewModel()) {
    val fields = formViewModel.formFields
    Scaffold(
        containerColor = backgroundColor,
    ) { padding ->
        Card(modifier = Modifier.padding(padding).padding(horizontal = 14.dp), colors = CardDefaults.outlinedCardColors(containerColor = Color.White)) {
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(fields, key = { it.tag }) { formField ->
                        when(formField.type) {
                            FormFieldType.EMAIL, FormFieldType.AMOUNT, FormFieldType.TEXT, FormFieldType.NUMBER, FormFieldType.PASSWORD -> {
                                FormTextField(
                                    formField = formField,
                                    onValueChange = { newValue ->
                                        formViewModel.updateFieldValue(formField.tag, newValue)
                                    }
                                )
                            }
                            FormFieldType.CHECKBOX -> {
                                Text("Check box...")
                            }
                            FormFieldType.DROPDOWN -> {
                                DropDownField(formField)
                            }
                            FormFieldType.AMOUNT_CHIPS -> {
                                AmountChipFormField(formField)
                            }
                        }
                    }

                    item {
                        ProceedCancelButton(formViewModel)
                    }
                }
        }
    }
}

@Composable
private fun FormTextField(
    formField: FormField,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(formField.label, style = AppTextStyle.textNormalDim())
        BasicTextField(
            value = formField.value,
            onValueChange = onValueChange,
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
        formField.errorMessage?.let { error ->
            Text(
                text = error,

                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownField(formField: FormField) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(formField.options?.first() ?: "") }

    if (showSheet) {
        ModalBottomSheet(
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            containerColor = Color.White,
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                formField.options?.forEach { option ->
                    Text(
                        text = option,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable {
                                scope.launch {
                                    selectedOption.value = option
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    showSheet = false
                                }
                            }
                    )
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 12.dp)) {
        Text(formField.label, style = AppTextStyle.textNormalDim())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .clickable {
                  scope.launch { showSheet = true }
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption.value,
                    color = Color.Black
                )
                Icon(Icons.Default.ArrowDropDown, modifier = Modifier.size(14.dp), tint = textColorPrimary, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ProceedCancelButton(formViewModel: FormViewModel) {
    val navController = LocalNavController.current

    Spacer(Modifier.height(18.dp))
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = textColorPrimary, contentColor = Color.White),
            onClick = {
                if (formViewModel.validateFields()) {
                    val username = formViewModel.getFieldValue("username")
                } else {
                    println("Form has errors.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Button(
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent, contentColor = Color.DarkGray),
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}