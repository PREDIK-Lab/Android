package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import kotlinx.coroutines.flow.Flow

interface GrafikUseCase {
    suspend fun getGrafik(kodeSaham: String): Flow<Resource<List<Grafik>>>
}