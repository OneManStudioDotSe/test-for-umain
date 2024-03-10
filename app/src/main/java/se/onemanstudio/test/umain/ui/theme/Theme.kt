package se.onemanstudio.test.umain.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = positive,

    background = background,
    surface = surface,
    error = error,

    onSurface = onSurface,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer,
    onBackground = onBackground,

    surfaceTint = surface
)

@Composable
fun UmainTheme(
    content: @Composable () -> Unit
) {
    /*
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
     */

    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}