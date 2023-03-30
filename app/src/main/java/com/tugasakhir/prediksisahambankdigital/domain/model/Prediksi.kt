package com.tugasakhir.prediksisahambankdigital.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Prediksi(
    val hargaPenutupanSaatIni: Float,
    val hargaPenutupanSebelumnya: Float,
    val prediksiLSTM: List<DetailPrediksi>,
    //val prediksiLSTM: List<Float>,
    val rmseLSTM: Float,
    val prediksiGRU: List<DetailPrediksi>,
    //val prediksiGRU: List<Float>,
    val rmseGRU: Float
) : Parcelable