package com.urmusicapp.theme

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun extractDominantColor(
    bitmap: Bitmap,
    fallback: Color = Color(0xFF6750A4),
): Color = withContext(Dispatchers.Default) {
    val palette = Palette.from(bitmap)
        .clearFilters()
        .generate()
    val dominant = palette.getDominantColor(fallback.toArgb())
    Color(dominant)
}
