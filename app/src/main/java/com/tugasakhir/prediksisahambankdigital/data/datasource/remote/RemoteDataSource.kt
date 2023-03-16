package com.tugasakhir.prediksisahambankdigital.data.datasource.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: APIService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: APIService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    suspend fun getAllPrediksi(kodeSaham: String): Flow<APIResponse<List<ListPrediksiResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getListPrediksi(kodeSaham)
                if (response.isNotEmpty()) {
                    emit(APIResponse.Success(response))
                } else {
                    emit(APIResponse.Empty)
                }
            } catch (e: Exception) {
                emit(APIResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPrediksi(kodeSaham: String): Flow<APIResponse<PrediksiResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getPrediksi(kodeSaham)
                if (response != null) {
                    emit(APIResponse.Success(response))
                } else {
                    emit(APIResponse.Empty)
                }
            } catch (e: Exception) {
                emit(APIResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGrafik(kodeSaham: String): Flow<APIResponse<GrafikResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getGrafik(kodeSaham)
                if (response != null) {
                    emit(APIResponse.Success(response))
                } else {
                    emit(APIResponse.Empty)
                }
            } catch (e: Exception) {
                emit(APIResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getInformasi(kodeSaham: String): Flow<APIResponse<InformasiResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getInformasi(kodeSaham)
                if (response != null) {
                    emit(APIResponse.Success(response))
                } else {
                    emit(APIResponse.Empty)
                }
            } catch (e: Exception) {
                emit(APIResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

//    suspend fun getPrediksi(id: String): Flow<APIResponse<PrediksiResponse>> {
//        //get data from remote api
//        return flow {
//            try {
//                val response = apiService.getPrediksi(id)
//                if (response.id.isNotEmpty()) {
//                    emit(APIResponse.Success(response))
//                } else {
//                    emit(APIResponse.Empty)
//                }
//            } catch (e: Exception) {
//                emit(APIResponse.Error(e.toString()))
//                Log.e("RemoteDataSource", e.toString())
//            }
//        }.flowOn(Dispatchers.IO)
//    }
}