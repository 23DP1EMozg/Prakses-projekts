package com.spuldz.praksesprojekts.core.models

data class GridCellModel(
    val value: Int,
    val isEditable: Boolean = false,
    val rowNumber: Int,
    val colNumber: Int,
    val squareStart: Pair<Int, Int>,
    var isSelected: Boolean = false,
    val isPlayerPlaced: Boolean = false,
    var isLightUp: Boolean = false,
    val isError: Boolean = false,
    val isHighlighted: Boolean = false,
    val pencilValue: String? = null
)
