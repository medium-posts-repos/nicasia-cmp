package junkeritechnepal.nicasiacmp.modules.forms

enum class FormFieldType {
    TEXT,
    EMAIL,
    PASSWORD,
    NUMBER,
    CHECKBOX,
    DROPDOWN,
    AMOUNT,
    AMOUNT_CHIPS
}

data class AmountPayableOption(
    val amount: String,
    val label: String? = null
) {
    companion object {
        val defaultAmountPayableOptions: List<AmountPayableOption>
            get() = listOf(
                AmountPayableOption("20", "100"),
                AmountPayableOption("50", "100"),
                AmountPayableOption("100", "100"),
                AmountPayableOption("120", "100"),
                AmountPayableOption("150", "100"),
                AmountPayableOption("200", "100"),
                AmountPayableOption("250", "100"),
                AmountPayableOption("300", "100"),
                AmountPayableOption("500", "100"),
                AmountPayableOption("800", "100"),
            )
    }
}

data class FormField(
    val type: FormFieldType,
    val tag: String,
    val label: String,
    var value: String,
    val placeHolder: String? = null,
    val isEditable: Boolean = true,
    val errorMessage: String? = "",
    val options: List<String>? = null,
    val payableAmounts: List<AmountPayableOption>? = null
)