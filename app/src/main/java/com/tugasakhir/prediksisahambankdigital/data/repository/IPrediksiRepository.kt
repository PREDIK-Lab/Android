package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import kotlinx.coroutines.flow.Flow

interface IPrediksiRepository {
    fun getAllPrediksi(kodeSaham: String): Flow<Resource<List<Prediksi>>>

    fun getPrediksi(kodeSaham: String): Flow<Resource<Prediksi>>
}