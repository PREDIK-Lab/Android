package com.tugasakhir.prediksisahambankdigital.ui.util

import java.math.RoundingMode

fun roundDecimal(number: Float, scale: Int = 1) = number.toBigDecimal()
    .setScale(1, RoundingMode.CEILING)
    .toFloat()