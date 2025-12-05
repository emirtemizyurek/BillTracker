package org.example.billtracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Indigo = Color(0xFF4F46E5)
val Emerald = Color(0xFF10B981)
val Red = Color(0xFFEF4444)
val DarkGray = Color(0xFF1F2937)
val MediumGray = Color(0xFF6B7280)
val LightGray = Color(0xFFF3F4F6)
val DarkSurface = Color(0xFF374151)
val DarkBackground = Color(0xFF111827)

private val LightColors = lightColors(
    primary = Indigo,
    background = LightGray,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = DarkGray,
    onSurface = DarkGray
)

private val DarkColors = darkColors(
    primary = Indigo,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AppTheme(
    theme: String = "system",
    content: @Composable () -> Unit
) {
    val darkTheme = when (theme) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
