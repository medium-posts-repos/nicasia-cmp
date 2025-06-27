package junkeritechnepal.nicasiacmp.modules.forms

enum class FormFieldType {
    TEXT,
    EMAIL,
    PASSWORD,
    NUMBER,
    CHECKBOX,
    DROPDOWN
}

data class FormField(
    val type: FormFieldType,
    val tag: String, // Unique identifier for the field
    val label: String,
    var value: String, // Mutable, as this is what the user changes
    val isEditable: Boolean = true, // Optional: to control if the field can be edited
    val errorMessage: String? = null, // Optional: for validation errors
    val options: List<String>? = null
)