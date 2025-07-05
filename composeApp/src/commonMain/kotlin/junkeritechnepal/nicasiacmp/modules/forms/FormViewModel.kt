import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import junkeritechnepal.nicasiacmp.modules.forms.AmountPayableOption
import junkeritechnepal.nicasiacmp.modules.forms.FormField
import junkeritechnepal.nicasiacmp.modules.forms.FormFieldType
import org.koin.core.component.KoinComponent

class FormViewModel : ViewModel(), KoinComponent {

    val formFields = mutableStateListOf<FormField>()

    init {
        // Example: Load initial fields (e.g., from a config, API, etc.)
        formFields.addAll(
            listOf(
                FormField(FormFieldType.TEXT, "username", "Username", "", placeHolder = "Enter user name"),
                FormField(FormFieldType.EMAIL, "email", "Email Address", "", placeHolder = "Enter email address"),
                FormField(FormFieldType.PASSWORD, "password", "Password", ""),
                FormField(FormFieldType.DROPDOWN, "dropdown", "Password", "", options = listOf("Kathmandu", "Bhaktapur", "Lalitpur")),
                FormField(FormFieldType.DROPDOWN, "branch", "Branch", "", options = listOf("001", "002", "003")),
                FormField(FormFieldType.AMOUNT_CHIPS, "amount", "Amount", "", payableAmounts = AmountPayableOption.defaultAmountPayableOptions),
                FormField(FormFieldType.AMOUNT_CHIPS, "amount1", "Amount", "", payableAmounts = AmountPayableOption.defaultAmountPayableOptions),
                FormField(FormFieldType.AMOUNT_CHIPS, "amount2", "Amount", "", payableAmounts = AmountPayableOption.defaultAmountPayableOptions)
            )
        )
    }

    fun updateFieldValue(tag: String, newValue: String) {
        val fieldIndex = formFields.indexOfFirst { it.tag == tag }
        if (fieldIndex != -1) {
            // To ensure Compose recomposes correctly when an item's property changes,
            // it's often best to replace the item in the list if it's a data class.
            // Or, if FormField.value was a MutableState<String>, updating it would trigger recomposition.
            val updatedField = formFields[fieldIndex].copy(
                value = newValue,
                errorMessage = null
            ) // Clear error on edit
            formFields[fieldIndex] = updatedField
        }
    }

    fun validateFields(): Boolean {
        var isValid = true
        val updatedFields = formFields.map { field ->
            var currentFieldValid = true
            var error: String? = null
            when (field.type) {
                FormFieldType.TEXT -> if (field.tag == "username" && field.value.length < 3) {
                    error = "Username too short"
                    currentFieldValid = false
                }

                FormFieldType.EMAIL -> if (field.tag == "email" ) {
                    error = "Invalid email format"
                    currentFieldValid = false
                }

                FormFieldType.PASSWORD -> if (field.value.length < 6) {
                    error = "Password too short"
                    currentFieldValid = false
                }

                FormFieldType.NUMBER -> if (field.value.toIntOrNull() == null && field.value.isNotEmpty()) {
                    error = "Invalid number"
                    currentFieldValid = false
                }
                else -> {
                   error = " "
                }
            }
            if (!currentFieldValid) isValid = false
            field.copy(errorMessage = error)
        }
        formFields.clear()
        formFields.addAll(updatedFields)
        return isValid
    }

    fun getFieldValue(tag: String): String? {
        return formFields.find { it.tag == tag }?.value
    }
}
