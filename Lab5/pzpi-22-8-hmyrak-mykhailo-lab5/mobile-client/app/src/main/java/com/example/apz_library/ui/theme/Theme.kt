package com.example.apz_library.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Коричнева палітра
val Brown80 = Color(0xFF6D4C41)
val Brown60 = Color(0xFF8D6E63)
val Brown40 = Color(0xFFD7CCC8)
val Brown20 = Color(0xFFEFEBE9)
val AccentGold = Color(0xFFFFD700)


private val LightBrownColorScheme = lightColorScheme(
    primary = Brown80,
    onPrimary = Color.White,
    primaryContainer = Brown60,
    onPrimaryContainer = Color.White,
    secondary = AccentGold,
    onSecondary = Brown80,
    background = Brown20,
    onBackground = Brown80,
    surface = Color.White,
    onSurface = Brown80,
    surfaceVariant = Brown40,
    onSurfaceVariant = Brown80,
    error = Color(0xFFB00020),
    onError = Color.White
)

private val DarkBrownColorScheme = darkColorScheme(
    primary = Brown40,
    onPrimary = Brown80,
    primaryContainer = Brown60,
    onPrimaryContainer = Color.White,
    secondary = AccentGold,
    onSecondary = Brown80,
    background = Brown80,
    onBackground = Color.White,
    surface = Brown60,
    onSurface = Color.White,
    surfaceVariant = Brown40,
    onSurfaceVariant = Brown80,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun Apz_LibraryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // вимикаємо dynamic для стабільності кольорів
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkBrownColorScheme
        else -> LightBrownColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}