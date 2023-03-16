package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.APIResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.GrafikResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.RemoteDataSource
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.RemoteNetworkBoundResource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.util.AppExecutors
import com.tugasakhir.prediksisahambankdigital.domain.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GrafikRepository(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IGrafikRepository {
    companion object {
        @Volatile
        private var instance: GrafikRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            appExecutors: AppExecutors
        ): GrafikRepository =
            instance ?: synchronized(this) {
                instance ?: GrafikRepository(remoteData, appExecutors)
            }
    }

    override fun getGrafik(kodeSaham: String): Flow<Resource<List<Grafik>>> =
        object : RemoteNetworkBoundResource<List<Grafik>, GrafikResponse>() {
            override suspend fun createCall(): Flow<APIResponse<GrafikResponse>> =
                remoteDataSource.getGrafik(kodeSaham)

            override suspend fun saveCallResult(data: GrafikResponse): Flow<List<Grafik>> =
                remoteDataSource.getGrafik(kodeSaham)
                    .map { DataMapper.mapGrafikResponseToListGrafik(data) }

        }.asFlow()
}