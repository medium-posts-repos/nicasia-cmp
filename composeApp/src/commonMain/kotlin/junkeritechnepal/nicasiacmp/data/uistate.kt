package junkeritechnepal.nicasiacmp.data

data class GenericUIState<T>(
    var data: T,
    var isVisible: Boolean
) {
    fun updateVisibility(isVisible: Boolean): GenericUIState<T> {
        this.isVisible = isVisible
        return this
    }

    fun updateData(data: T): GenericUIState<T> {
        this.data = data
        return this
    }
}