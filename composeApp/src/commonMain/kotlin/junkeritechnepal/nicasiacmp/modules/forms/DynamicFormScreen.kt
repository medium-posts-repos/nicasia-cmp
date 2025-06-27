package junkeritechnepal.nicasiacmp.modules.forms

import FormViewModel
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.textColorPrimary
import kotlinx.coroutines.launch

@Composable
fun DynamicFormScreen(formViewModel: FormViewModel = viewModel()) {
    val fields = formViewModel.formFields
    var expanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(fields, key = { it.tag }) { formField ->
                when(formField.type) {
                    FormFieldType.EMAIL, FormFieldType.TEXT, FormFieldType.NUMBER, FormFieldType.PASSWORD -> {
                        FormFieldItem(
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
                }
            }
        }

        Button(
            onClick = {
                if (formViewModel.validateFields()) {
                    // All fields are valid, proceed with submission
                    val username = formViewModel.getFieldValue("username")
                    // ... get other values and submit
                    println("Form Submitted: $fields")
                } else {
                    println("Form has errors.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@Composable
private fun FormFieldItem(
    formField: FormField,
    onValueChange: (String) -> Unit
) {
    Column {
        TextField(
            value = formField.value,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text("as") },
            onValueChange = onValueChange, // Delegate value changes up
            modifier = Modifier.fillMaxWidth(),
            label = { Text(formField.label) },
            isError = formField.errorMessage != null,
            enabled = formField.isEditable,
            visualTransformation = if (formField.type == FormFieldType.PASSWORD) PasswordVisualTransformation() else VisualTransformation.None,
        )
        formField.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
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

    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(formField.label, style = AppTextStyle.textNormalDim())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(0.8.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp)
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
                    color = Color.DarkGray
                )
                Icon(Icons.Default.ArrowDropDown, tint = textColorPrimary, contentDescription = null)
            }
        }
    }
}