package com.rentminder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * The design for the Dark Color Palette
 */
private val DarkColorPalette = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200
)

/**
 * The design for the Light Color Palette
 */
private val LightColorPalette = lightColors(
        primary = LavenderBlue,
        primaryVariant = LavenderBlue,
        secondary = Teal200,
        onPrimary = Color.Black,
        background = SoftGray

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

/**
 * Function for choosing the theme for the UI, either dark or light palette
 */
@Composable
fun RentMinderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}