package com.spuldz.praksesprojekts.core.models

data class GridCellModel(
    val value: Int,
    val isEditable: Boolean,
    val rowNumber: Int,
    val colNumber: Int,
    val squareStart: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridCellModel

        if (value != other.value) return false
        if (isEditable != other.isEditable) return false
        if (rowNumber != other.rowNumber) return false
        if (colNumber != other.colNumber) return false
        if (!squareStart.contentEquals(other.squareStart)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value
        result = 31 * result + isEditable.hashCode()
        result = 31 * result + rowNumber
        result = 31 * result + colNumber
        result = 31 * result + squareStart.contentHashCode()
        return result
    }
}
