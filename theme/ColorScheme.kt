package com.urmusicapp.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

private const val DefaultBlendRatio = 0.16f

fun createColorSchemeFromSeed(seedColor: Color, darkTheme: Boolean): ColorScheme {
    val onSeed = contentColorForSeed(seedColor, darkTheme)
    val surface = if (darkTheme) Color(0xFF121212) else Color(0xFFFEFBFF)
    val onSurface = if (darkTheme) Color(0xFFE6E1E5) else Color(0xFF1D1B20)
    val background = surface
    val onBackground = onSurface
    val secondary = blend(seedColor, onSurface, DefaultBlendRatio)
    val tertiary = blend(seedColor, if (darkTheme) Color(0xFF8A8A8A) else Color(0xFF6A5ACD), 0.24f)

    return if (darkTheme) {
        darkColorScheme(
            primary = seedColor,
            onPrimary = onSeed,
            primaryContainer = blend(seedColor, Color.Black, 0.32f),
            onPrimaryContainer = Color.White,
            secondary = secondary,
            onSecondary = contentColorForSeed(secondary, true),
            secondaryContainer = blend(secondary, Color.Black, 0.3f),
            onSecondaryContainer = Color.White,
            tertiary = tertiary,
            onTertiary = contentColorForSeed(tertiary, true),
            tertiaryContainer = blend(tertiary, Color.Black, 0.3f),
            onTertiaryContainer = Color.White,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
        )
    } else {
        lightColorScheme(
            primary = seedColor,
            onPrimary = onSeed,
            primaryContainer = blend(seedColor, Color.White, 0.32f),
            onPrimaryContainer = Color.Black,
            secondary = secondary,
            onSecondary = contentColorForSeed(secondary, false),
            secondaryContainer = blend(secondary, Color.White, 0.28f),
            onSecondaryContainer = Color.Black,
            tertiary = tertiary,
            onTertiary = contentColorForSeed(tertiary, false),
            tertiaryContainer = blend(tertiary, Color.White, 0.28f),
            onTertiaryContainer = Color.Black,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
        )
    }
}

private fun contentColorForSeed(seedColor: Color, darkTheme: Boolean): Color {
    val blended = blend(seedColor, if (darkTheme) Color.Black else Color.White, 0.75f)
    return if (luminance(blended) < 0.5f) Color.White else Color.Black
}

private fun luminance(color: Color): Float {
    return ColorUtils.calculateLuminance(color.toArgb()).toFloat()
}

private fun blend(from: Color, to: Color, ratio: Float): Color {
    return Color(ColorUtils.blendARGB(from.toArgb(), to.toArgb(), ratio))
}
