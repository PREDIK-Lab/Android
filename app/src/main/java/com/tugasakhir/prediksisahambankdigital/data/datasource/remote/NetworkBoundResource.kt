package com.tugasakhir.prediksisahambankdigital.data.datasource.remote

import com.tugasakhir.prediksisahambankdigital.data.Resource
import kotlinx.coroutines.flow.*

abstract class RemoteNetworkBoundResource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        when (val apiResponse = createCall().first()) {
            is APIResponse.Success -> {
                emitAll(saveCallResult(apiResponse.data).map { Resource.Success(it) })
            }
            is APIResponse.Empty -> {
                onFetchFailed()
                emit(Resource.Error("The data is not available"))
            }
            is APIResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun createCall(): Flow<APIResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result
}