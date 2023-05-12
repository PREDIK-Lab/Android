package com.tugasakhir.prediksisahambankdigital.ui.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val dayMonthFormat = DateTimeFormatter.ofPattern("dd-MM")
val dayMonthYearFormat = DateTimeFormatter.ofPattern("dd-MM-yy")

fun parseDayMonthFormat(date: String) = LocalDate.parse(date).format(dayMonthFormat)

fun parseDayMonthYearFormat(date: String) = LocalDate.parse(date).format(dayMonthYearFormat)