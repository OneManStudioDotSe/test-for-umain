package se.onemanstudio.test.umain.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import se.onemanstudio.test.umain.R

val helveticaFamily = FontFamily(
    Font(R.font.helvetica_regular, FontWeight.Normal),
    Font(R.font.helvetica_bold, FontWeight.Bold)
)

val interFamily = FontFamily(
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    //title1
    titleMedium = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 16.sp,
    ),

    //subtitle1
    labelMedium = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),

    //headline1
    headlineLarge = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 18.sp,
    ),

    //headline2
    headlineMedium = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
    ),

    //title2
    titleSmall = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    //footer1 -  Medium
    labelLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        color = footer
    ),

    //footer1 - Bold
    labelSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
    ),
)
