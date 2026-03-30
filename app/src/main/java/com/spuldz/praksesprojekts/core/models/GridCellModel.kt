package com.spuldz.praksesprojekts.core.models

data class GridCellModel(
    var value: Int,
    val isEditable: Boolean,
    val rowNumber: Int,
    val colNumber: Int,
    val squareStart: Pair<Int, Int>,
    var isSelected: Boolean = false,
)
