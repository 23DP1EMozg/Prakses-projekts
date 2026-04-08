package com.spuldz.praksesprojekts.core.models

data class GameModel(
    val difficulty: String,
    val seconds: Long = 0L,
    val time: String = "00:00",
    val mistakes: Int = 0,
    val isFinished: Boolean = false,
    val pencilMode: Boolean = false,
    val hintMode: Boolean = false,
    val hintsLeft: Int = 3,
    val isWin: Boolean = false
)
