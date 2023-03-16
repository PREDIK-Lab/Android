package com.tugasakhir.prediksisahambankdigital.data

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

//sealed class Resource<out T: Any?> {
//    object Loading : Resource<Nothing>()
//    data class Success<out T: Any>(val data: T) : Resource<T>()
//    data class Error(val errorMessage: String) : Resource<Nothing>()
//}
