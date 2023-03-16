package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import kotlinx.coroutines.flow.Flow

interface IGrafikRepository {
    fun getGrafik(kodeSaham: String): Flow<Resource<List<Grafik>>>
}