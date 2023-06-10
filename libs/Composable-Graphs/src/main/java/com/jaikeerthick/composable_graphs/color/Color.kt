package com.jaikeerthick.composable_graphs.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LineGraphColor() = if(isSystemInDarkTheme()) Color(0xFF7198FD) else Color(0xFF032E9D) //Color(0xFF29CA0E)

val GraphAccent = Color(0xFF9C27B0)
val GraphAccent2 = Color(0xFF032E9D) //Color(0xFF29CA0E)

val Gradient1 = Color(0x549C27B0)
val Gradient2 = Color(0x00FFFFFF)
val Gradient3 = Color(0x5480A2FA) //Color(0x54388E3C)

val LightGray = Color(0x43B3B3B3)

val PointHighlight = Color(0x749C27B0)
val PointHighlight2 = Color(0x753D579B) //Color(0x75388E3C)