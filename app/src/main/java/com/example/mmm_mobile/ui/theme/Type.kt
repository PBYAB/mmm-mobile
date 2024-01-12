package com.example.mmm_mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mmm_mobile.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

var josefinSansFontFamily = FontFamily(
    Font(R.font.josefin_sans_bold, FontWeight.Black),
    Font(R.font.josefin_sans_light, FontWeight.Light),
    Font(R.font.josefin_sans_medium, FontWeight.Medium),
    Font(R.font.josefin_sans_regular, FontWeight.Normal),
    )

var poppinsFontFamily = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_regular, FontWeight.Medium),
    Font(R.font.poppins_italic, FontWeight.Light),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
    )