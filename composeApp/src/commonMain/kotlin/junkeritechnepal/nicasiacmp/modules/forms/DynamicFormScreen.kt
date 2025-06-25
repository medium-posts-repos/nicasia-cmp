package junkeritechnepal.nicasiacmp.modules.forms

import FormViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DynamicFormScreen(formViewModel: FormViewModel = viewModel()) {
    val fields = formViewModel.formFields

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(fields, key = { it.tag }) { formField ->
                when(formField.type) {
                    FormFieldType.EMAIL, FormFieldType.TEXT, FormFieldType.NUMBER, FormFieldType.PASSWORD -> {
                        FormFieldItem(
                            formField = formField,
                            onValueChange = { newValue ->
                                formViewModel.updateFieldValue(formField.tag, newValue)
                            }
                        )
                        Spacer(modifier =Modifier.height(8.dp))
                    }
                    FormFieldType.CHECKBOX -> {
                        Text("Check box...")
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