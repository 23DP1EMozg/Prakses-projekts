package com.spuldz.praksesprojekts.ui.theme

import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
// Colors for text
val White = Color(0xffffffff)

val DefaultTheme = Theme(
    name = "Default",
    Primary = Color(0xfffab22d),
    Secondary = Color(0xffb38022),
    Text = White,
    Background = Color(0xff303030),
    PlacedCellText = Color(0xff3485a8),
    HighlightColor = Color(0xffdea63e),
    Error = Color.Red,
    BackgroundLighter = Color(0xff5c5b5b)
)

val MidnightNeonTheme = Theme(
    name = "Midnight Neon",
    Primary = Color(0xff9d4edd),       // Electric Purple
    Secondary = Color(0xff5a189a),     // Deep Violet
    Text = Color(0xffffffff),          // Pure White
    Background = Color(0xff10002b),    // Deep Space Navy
    PlacedCellText = Color(0xff00f5d4),// Bright Teal/Cyan
    HighlightColor = Color(0xffc77dff),// Soft Lavender
    Error = Color(0xffff0054),         // Vivid Punchy Red
    BackgroundLighter = Color(0xff240046) // Dark Purple-Grey
)

val ArcticFrostTheme = Theme(
    name = "Arctic Frost",
    Primary = Color(0xff48cae4),       // Sky Blue
    Secondary = Color(0xff0077b6),     // Deep Ocean Blue
    Text = Color(0xffffffff),          // Pure White
    Background = Color(0xff03045e),    // Deep Navy
    PlacedCellText = Color(0xffcaf0f8),// Pale Ice Blue
    HighlightColor = Color(0xff90e0ef),// Soft Cyan
    Error = Color(0xffff4d6d),         // Rose Red
    BackgroundLighter = Color(0xff023e8a) // Mid-tone Navy
)

val CyberPunkTheme = Theme(
    name = "Cyber Punk",
    Primary = Color(0xffff006e),       // Hot Pink
    Secondary = Color(0xff8338ec),     // Electric Purple
    Text = Color(0xffffffff),          // Pure White
    Background = Color(0xff050505),    // Pure Obsidian Black
    PlacedCellText = Color(0xfffee440),// Laser Yellow
    HighlightColor = Color(0xff3a86ff),// Neon Blue
    Error = Color(0xffff4800),         // Safety Orange
    BackgroundLighter = Color(0xff1a1a1a) // Soft Charcoal
)

val EmeraldForestTheme = Theme(
    name = "Emerald Forest",
    Primary = Color(0xff2a9d8f),       // Persian Green
    Secondary = Color(0xff264653),     // Dark Slate Green
    Text = Color(0xffe9f5f2),          // Minty White
    Background = Color(0xff141a16),    // Dark Forest Black
    PlacedCellText = Color(0xffe9c46a),// Sand Gold (excellent contrast vs green)
    HighlightColor = Color(0xff80ed99),// Light Emerald
    Error = Color(0xffe76f51),         // Burnt Coral
    BackgroundLighter = Color(0xff1f2922) // Deep Moss
)
