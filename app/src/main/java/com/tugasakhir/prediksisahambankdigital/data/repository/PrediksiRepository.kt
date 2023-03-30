package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.*
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import com.tugasakhir.prediksisahambankdigital.domain.util.AppExecutors
import com.tugasakhir.prediksisahambankdigital.domain.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrediksiRepository(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IPrediksiRepository {
    companion object {
        @Volatile
        private var instance: PrediksiRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            appExecutors: AppExecutors
        ): PrediksiRepository =
            instance ?: synchronized(this) {
                instance ?: PrediksiRepository(remoteData, appExecutors)
            }
    }

    override fun getAllPrediksi(kodeSaham: String): Flow<Resource<List<Prediksi>>> =
        object : RemoteNetworkBoundResource<List<Prediksi>, List<ListPrediksiResponse>>() {
            override suspend fun createCall(): Flow<APIResponse<List<ListPrediksiResponse>>> =
                remoteDataSource.getAllPrediksi(kodeSaham)

            override suspend fun saveCallResult(data: List<ListPrediksiResponse>): Flow<List<Prediksi>> =
                remoteDataSource.getAllPrediksi(kodeSaham)
                    .map { DataMapper.mapResponseToPrediksi(data) }

        }.asFlow()

    override fun getPrediksi(kodeSaham: String): Flow<Resource<Prediksi>> =
        object : RemoteNetworkBoundResource<Prediksi, PrediksiResponse>() {
            override suspend fun createCall(): Flow<APIResponse<PrediksiResponse>> =
                remoteDataSource.getPrediksi(kodeSaham)

            override suspend fun saveCallResult(data: PrediksiResponse): Flow<Prediksi> =
                remoteDataSource.getPrediksi(kodeSaham)
                    .map { DataMapper.mapPrediksiResponseToPrediksi(data) }

        }.asFlow()
}