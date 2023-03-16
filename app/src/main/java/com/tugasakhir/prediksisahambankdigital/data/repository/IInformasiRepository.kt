package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import kotlinx.coroutines.flow.Flow

interface IInformasiRepository {
    fun getInformasi(kodeSaham: String): Flow<Resource<Informasi>>
}