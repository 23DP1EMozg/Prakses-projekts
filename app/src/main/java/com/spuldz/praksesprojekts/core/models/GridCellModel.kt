package com.spuldz.praksesprojekts.core.models

data class GridCellModel(
    var value: Int,
    var isEditable: Boolean = false,
    val rowNumber: Int,
    val colNumber: Int,
    val squareStart: Pair<Int, Int>,
    var isSelected: Boolean = false,
    val isPlayerPlaced: Boolean = false,
    val isLightUp: Boolean = false,
    val isError: Boolean = false
)
