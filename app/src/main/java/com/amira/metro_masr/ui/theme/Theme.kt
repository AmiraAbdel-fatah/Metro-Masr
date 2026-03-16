package com.amira.metro_masr.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE53935),
    secondary = Color(0xFFB71C1C),
    tertiary = Color(0xFF1E88E5),
    primaryContainer = Color(0xFF93000A), // Dark red container
    onPrimaryContainer = Color(0xFFFFDAD4), // Light red text
    secondaryContainer = Color(0xFF534341),
    onSecondaryContainer = Color(0xFFFFDAD4),
    background = Color.Black,
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD32F2F),
    secondary = Color(0xFFB71C1C),
    tertiary = Color(0xFF1E88E5),
    primaryContainer = Color(0xFFFFDAD4), // Very light red/pink container
    onPrimaryContainer = Color(0xFF410002), // Deep red text
    secondaryContainer = Color(0xFFFFEDEA),
    onSecondaryContainer = Color(0xFF410002),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun Metro_MasrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
