package com.tugasakhir.prediksisahambankdigital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailPrediksi(
    val prediksiHargaPenutupan: Float,
    val tanggal: String,
): Parcelable