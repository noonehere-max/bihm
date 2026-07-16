package com.example.bihm.ui.theme

import androidx.compose.ui.graphics.Color

// Surfaces (independent of accent)
val BackgroundLight = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF1F3F4)
val OnBackgroundLight = Color(0xFF1F1F1F)
val OnSurfaceLight = Color(0xFF1F1F1F)
val OnSurfaceVariantLight = Color(0xFF5F6368)
val OutlineLight = Color(0xFFDADCE0)

val BackgroundDark = Color(0xFF000000)
val SurfaceDark = Color(0xFF000000)
val SurfaceVariantDark = Color(0xFF1A1A1A)
val OnBackgroundDark = Color(0xFFE8EAED)
val OnSurfaceDark = Color(0xFFE8EAED)
val OnSurfaceVariantDark = Color(0xFF9AA0A6)
val OutlineDark = Color(0xFF5F6368)

enum class AccentColor {
    PIXEL_BLUE,
    MINT_GREEN,
    CORAL_ORANGE,
    LAVENDER_PURPLE,
    CRIMSON_RED,
    ROSE_PINK,
    OCEAN_TEAL,
    AMBER_GOLD
}

data class AccentPalette(
    val lightPrimary: Color,
    val lightOnPrimary: Color,
    val lightPrimaryContainer: Color,
    val lightOnPrimaryContainer: Color,
    val darkPrimary: Color,
    val darkOnPrimary: Color,
    val darkPrimaryContainer: Color,
    val darkOnPrimaryContainer: Color
)

fun paletteFor(accent: AccentColor): AccentPalette = when (accent) {
    AccentColor.PIXEL_BLUE -> AccentPalette(
        lightPrimary = Color(0xFF1A73E8),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFD3E3FD),
        lightOnPrimaryContainer = Color(0xFF041E49),
        darkPrimary = Color(0xFF8AB4F8),
        darkOnPrimary = Color(0xFF062E6F),
        darkPrimaryContainer = Color(0xFF1A3766),
        darkOnPrimaryContainer = Color(0xFFD2E3FC)
    )
    AccentColor.MINT_GREEN -> AccentPalette(
        lightPrimary = Color(0xFF1E8E3E),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFC9E7D0),
        lightOnPrimaryContainer = Color(0xFF04200C),
        darkPrimary = Color(0xFF81C995),
        darkOnPrimary = Color(0xFF0A3818),
        darkPrimaryContainer = Color(0xFF145524),
        darkOnPrimaryContainer = Color(0xFF9DE3A8)
    )
    AccentColor.CORAL_ORANGE -> AccentPalette(
        lightPrimary = Color(0xFFE37400),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFFFDCBD),
        lightOnPrimaryContainer = Color(0xFF2D1600),
        darkPrimary = Color(0xFFFFB74D),
        darkOnPrimary = Color(0xFF4D2600),
        darkPrimaryContainer = Color(0xFF7A3E00),
        darkOnPrimaryContainer = Color(0xFFFFE0B2)
    )
    AccentColor.LAVENDER_PURPLE -> AccentPalette(
        lightPrimary = Color(0xFF7B4FB8),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFE8DEF8),
        lightOnPrimaryContainer = Color(0xFF21005D),
        darkPrimary = Color(0xFFD0BCFF),
        darkOnPrimary = Color(0xFF381E72),
        darkPrimaryContainer = Color(0xFF4F378B),
        darkOnPrimaryContainer = Color(0xFFEADDFF)
    )
    AccentColor.CRIMSON_RED -> AccentPalette(
        lightPrimary = Color(0xFFD93025),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFFFDAD4),
        lightOnPrimaryContainer = Color(0xFF410001),
        darkPrimary = Color(0xFFF28B82),
        darkOnPrimary = Color(0xFF601410),
        darkPrimaryContainer = Color(0xFF8C1D18),
        darkOnPrimaryContainer = Color(0xFFFFDAD4)
    )
    AccentColor.ROSE_PINK -> AccentPalette(
        lightPrimary = Color(0xFFC2185B),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFFFD9E2),
        lightOnPrimaryContainer = Color(0xFF3E001D),
        darkPrimary = Color(0xFFF48FB1),
        darkOnPrimary = Color(0xFF5C1132),
        darkPrimaryContainer = Color(0xFF8F0045),
        darkOnPrimaryContainer = Color(0xFFFFD9E2)
    )
    AccentColor.OCEAN_TEAL -> AccentPalette(
        lightPrimary = Color(0xFF00897B),
        lightOnPrimary = Color(0xFFFFFFFF),
        lightPrimaryContainer = Color(0xFFB2DFDB),
        lightOnPrimaryContainer = Color(0xFF002019),
        darkPrimary = Color(0xFF80CBC4),
        darkOnPrimary = Color(0xFF003730),
        darkPrimaryContainer = Color(0xFF00564D),
        darkOnPrimaryContainer = Color(0xFFB2DFDB)
    )
    AccentColor.AMBER_GOLD -> AccentPalette(
        lightPrimary = Color(0xFFF9AB00),
        lightOnPrimary = Color(0xFF1F1F1F),
        lightPrimaryContainer = Color(0xFFFDE293),
        lightOnPrimaryContainer = Color(0xFF3F2E00),
        darkPrimary = Color(0xFFFDD663),
        darkOnPrimary = Color(0xFF3F2E00),
        darkPrimaryContainer = Color(0xFF7A5900),
        darkOnPrimaryContainer = Color(0xFFFDE293)
    )
}
