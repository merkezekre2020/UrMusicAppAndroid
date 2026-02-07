package com.urmusicapp.theme

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class ThemeState(
    seedColor: Color,
    darkTheme: Boolean,
) {
    var seedColor by mutableStateOf(seedColor)
        private set
    var darkTheme by mutableStateOf(darkTheme)
        private set
    var colorScheme by mutableStateOf(createColorSchemeFromSeed(seedColor, darkTheme))
        private set

    fun updateThemeMode(isDark: Boolean) {
        darkTheme = isDark
        colorScheme = createColorSchemeFromSeed(seedColor, darkTheme)
    }

    suspend fun updateFromAlbumArt(bitmap: Bitmap) {
        val dominant = extractDominantColor(bitmap, seedColor)
        seedColor = dominant
        colorScheme = createColorSchemeFromSeed(seedColor, darkTheme)
    }
}
