package com.tugasakhir.prediksisahambankdigital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrafikMap(
    val tanggal: String,
    val kategori: String,
    val hargaPenutupan: Float
) : Parcelable