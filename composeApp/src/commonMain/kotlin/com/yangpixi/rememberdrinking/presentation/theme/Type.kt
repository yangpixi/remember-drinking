package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import rememberdrinking.composeapp.generated.resources.Montserrat_Bold
import rememberdrinking.composeapp.generated.resources.Montserrat_Medium
import rememberdrinking.composeapp.generated.resources.Montserrat_Regular
import rememberdrinking.composeapp.generated.resources.Res

@Composable
fun getMontserrat() : FontFamily {
    return FontFamily(
        Font(
            resource = Res.font.Montserrat_Medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.Montserrat_Bold,
            weight = FontWeight.Bold
        ),
        Font(
            resource = Res.font.Montserrat_Regular,
            weight = FontWeight.Normal
        )
    )
}

@Composable
fun getTypography(): Typography {
    val montserrat = getMontserrat()

    val displayFontFamily = montserrat
    val bodyFontFamily = montserrat

    val baseline = Typography()

    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}

