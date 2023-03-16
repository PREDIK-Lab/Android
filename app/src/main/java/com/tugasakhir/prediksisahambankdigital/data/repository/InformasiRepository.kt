package com.tugasakhir.prediksisahambankdigital.data.repository

import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.APIResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.InformasiResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.RemoteDataSource
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.RemoteNetworkBoundResource
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import com.tugasakhir.prediksisahambankdigital.domain.util.AppExecutors
import com.tugasakhir.prediksisahambankdigital.domain.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InformasiRepository(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IInformasiRepository {
    companion object {
        @Volatile
        private var instance: InformasiRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            appExecutors: AppExecutors
        ): InformasiRepository =
            instance ?: synchronized(this) {
                instance ?: InformasiRepository(remoteData, appExecutors)
            }
    }

    override fun getInformasi(kodeSaham: String): Flow<Resource<Informasi>> =
        object : RemoteNetworkBoundResource<Informasi, InformasiResponse>() {
            override suspend fun createCall(): Flow<APIResponse<InformasiResponse>> =
                remoteDataSource.getInformasi(kodeSaham)

            override suspend fun saveCallResult(data: InformasiResponse): Flow<Informasi> =
                remoteDataSource.getInformasi(kodeSaham)
                    .map { DataMapper.mapInformasiResponseToInformasi(data) }

        }.asFlow()
}