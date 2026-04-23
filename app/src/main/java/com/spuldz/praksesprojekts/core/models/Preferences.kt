package com.spuldz.praksesprojekts.core.models

import java.util.Locale

data class Preferences(
    val id: Int = 1,
    val theme: Int = 0,
    val inputLayout: String = "row",
    val languageCode: String = Locale.getDefault().language,
    val hintCount: Int = 3,
    val mistakeLimit: Int = 3
)
