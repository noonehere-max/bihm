package com.example.bihm.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

private fun lightColorSchemeFor(accent: AccentColor) =
    with(paletteFor(accent)) {
        lightColorScheme(
            primary = lightPrimary,
            onPrimary = lightOnPrimary,
            primaryContainer = lightPrimaryContainer,
            onPrimaryContainer = lightOnPrimaryContainer,
            secondary = OnSurfaceVariantLight,
            onSecondary = BackgroundLight,
            secondaryContainer = SurfaceVariantLight,
            onSecondaryContainer = OnSurfaceLight,
            tertiary = lightPrimary,
            onTertiary = lightOnPrimary,
            background = BackgroundLight,
            onBackground = OnBackgroundLight,
            surface = SurfaceLight,
            onSurface = OnSurfaceLight,
            surfaceVariant = SurfaceVariantLight,
            onSurfaceVariant = OnSurfaceVariantLight,
            outline = OutlineLight,
            surfaceContainerLowest = BackgroundLight,
            surfaceContainerLow = SurfaceVariantLight,
            surfaceContainer = SurfaceVariantLight,
            surfaceContainerHigh = SurfaceVariantLight,
            surfaceContainerHighest = SurfaceVariantLight,
            inverseSurface = OnBackgroundLight,
            inverseOnSurface = BackgroundLight
        )
    }

private fun darkColorSchemeFor(accent: AccentColor) =
    with(paletteFor(accent)) {
        darkColorScheme(
            primary = darkPrimary,
            onPrimary = darkOnPrimary,
            primaryContainer = darkPrimaryContainer,
            onPrimaryContainer = darkOnPrimaryContainer,
            secondary = OnSurfaceVariantDark,
            onSecondary = BackgroundDark,
            secondaryContainer = SurfaceVariantDark,
            onSecondaryContainer = OnSurfaceDark,
            tertiary = darkPrimary,
            onTertiary = darkOnPrimary,
            background = BackgroundDark,
            onBackground = OnBackgroundDark,
            surface = SurfaceDark,
            onSurface = OnSurfaceDark,
            surfaceVariant = SurfaceVariantDark,
            onSurfaceVariant = OnSurfaceVariantDark,
            outline = OutlineDark,
            surfaceContainerLowest = BackgroundDark,
            surfaceContainerLow = SurfaceDark,
            surfaceContainer = SurfaceDark,
            surfaceContainerHigh = SurfaceVariantDark,
            surfaceContainerHighest = SurfaceVariantDark,
            inverseSurface = OnBackgroundDark,
            inverseOnSurface = BackgroundDark
        )
    }

@Composable
fun BihmTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    accentColor: AccentColor = AccentColor.PIXEL_BLUE,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorSchemeFor(accentColor)
        else -> lightColorSchemeFor(accentColor)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
