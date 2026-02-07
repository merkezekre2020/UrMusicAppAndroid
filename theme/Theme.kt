package com.urmusicapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun rememberThemeState(
    seedColor: Color = Color(0xFF6750A4),
    darkTheme: Boolean = isSystemInDarkTheme(),
): ThemeState {
    return remember(seedColor, darkTheme) {
        ThemeState(seedColor = seedColor, darkTheme = darkTheme)
    }
}

@Composable
fun UrMusicTheme(
    themeState: ThemeState = rememberThemeState(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = themeState.colorScheme,
        content = content,
    )
}
