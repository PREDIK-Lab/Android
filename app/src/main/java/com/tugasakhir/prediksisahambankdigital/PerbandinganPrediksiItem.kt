package com.tugasakhir.prediksisahambankdigital

import androidx.compose.ui.graphics.Color

data class PerbandinganPrediksiItem(
    val judul: String? = null,
    val deskripsi: String? = null,
    val hargaPenutupan: Float,
    val gambarKeterangan: Int,
    val warna: Color? = null,
)