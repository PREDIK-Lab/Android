package com.tugasakhir.prediksisahambankdigital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Grafik(
    val tanggal: String,
    val hargaPenutupan: Float
) : Parcelable