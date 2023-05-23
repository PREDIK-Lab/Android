package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import kotlinx.coroutines.flow.Flow

interface InformasiUseCase {
    suspend fun getInformasi(kodeSaham: String): Flow<Resource<Informasi>>
}