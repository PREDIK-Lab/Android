package com.tugasakhir.prediksisahambankdigital.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET(PREDIKSI)
    suspend fun getListPrediksi(
        @Query("kode_saham") kodeSaham: String
    ): List<ListPrediksiResponse>

    @GET(PREDIKSI)
    suspend fun getPrediksi(@Query("kode_saham") kodeSaham: String): PrediksiResponse

    @GET(GRAFIK)
    suspend fun getGrafik(@Query("kode_saham") kodeSaham: String): GrafikResponse

    @GET(INFO)
    suspend fun getInformasi(@Query("kode_saham") kodeSaham: String): InformasiResponse

    companion object {
        private const val PREDIKSI = "prediksi"
        private const val GRAFIK = "grafik"
        private const val INFO = "info"

    }
}