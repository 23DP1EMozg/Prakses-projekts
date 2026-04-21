package com.spuldz.praksesprojekts.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TailwindSizing(
    val dp0: Dp = 0.dp,
    val dp05: Dp = 0.5.dp,
    val dp1: Dp = 1.dp,
    val dp2: Dp = 2.dp,
    val dp3: Dp = 3.dp,
    val dp4: Dp = 4.dp,
    val dp5: Dp = 5.dp,
    val dp6: Dp = 6.dp,
    val dp8: Dp = 8.dp,
    val dp10: Dp = 10.dp,
    val dp12: Dp = 12.dp,
    val dp14: Dp = 14.dp,
    val dp16: Dp = 16.dp,
    val dp17: Dp = 17.dp,
    val dp18: Dp = 18.dp,
    val dp20: Dp = 20.dp,
    val dp22: Dp = 22.dp,
    val dp24: Dp = 24.dp,
    val dp26: Dp = 26.dp,
    val dp28: Dp = 28.dp,
    val dp30: Dp = 30.dp,
    val dp32: Dp = 32.dp,
    val dp34: Dp = 34.dp,
    val dp36: Dp = 36.dp,
    val dp38: Dp = 38.dp,
    val dp40: Dp = 40.dp,
    val dp42: Dp = 42.dp,
    val dp44: Dp = 44.dp,
    val dp50: Dp = 50.dp,
    val dp64: Dp = 64.dp,
    val dp70: Dp = 70.dp,
    val dp100: Dp = 100.dp,
    val dp150: Dp = 150.dp,
    val dp250: Dp = 250.dp

)

val LocalSizing = compositionLocalOf { TailwindSizing() }

// We want this value to be on our theme, even if we do not use the theme object itself
/** A local provider of the [TailwindSizing] design system values. */
val sizing: TailwindSizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
