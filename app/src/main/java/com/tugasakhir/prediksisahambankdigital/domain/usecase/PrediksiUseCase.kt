package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import kotlinx.coroutines.flow.Flow

interface PrediksiUseCase {
    fun getPrediksi(kodeSaham: String): Flow<Resource<Prediksi>>
}