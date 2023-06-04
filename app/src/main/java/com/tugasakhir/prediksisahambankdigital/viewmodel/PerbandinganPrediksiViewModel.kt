package com.tugasakhir.prediksisahambankdigital.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import com.tugasakhir.prediksisahambankdigital.domain.usecase.GrafikUseCase
import com.tugasakhir.prediksisahambankdigital.domain.usecase.PrediksiUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PerbandinganPrediksiViewModel(
    private val prediksiUseCase: PrediksiUseCase,
    private val grafikUseCase: GrafikUseCase
) : ViewModel() {
    private val mutableKodeSaham = mutableStateOf("BBYB.JK")

    private val mutablePrediksi: MutableStateFlow<Resource<Prediksi>> =
        MutableStateFlow(Resource.Loading())
    val immutablePrediksi: StateFlow<Resource<Prediksi>>
        get() = mutablePrediksi.asStateFlow()

    private val mutableGrafik: MutableStateFlow<Resource<List<Grafik>>> =
        MutableStateFlow(Resource.Loading())
    val immutableGrafik: StateFlow<Resource<List<Grafik>>>
        get() = mutableGrafik.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


    //val prediksi = prediksiUseCase.getPrediksi("").asLiveData()

    fun setKodeSaham(kodeSaham: String) {
        mutableKodeSaham.value = kodeSaham
    }

    fun getPerbandinganPrediksi(result: (prediksi: StateFlow<Resource<Prediksi>>/*, grafik: StateFlow<Resource<List<Grafik>>>*/) -> Unit) {
        viewModelScope.launch {
            val callPrediksi = async { prediksiUseCase.getPrediksi(mutableKodeSaham.value) }
            val callGrafik = async { grafikUseCase.getGrafik(mutableKodeSaham.value) }

            callPrediksi.await().distinctUntilChanged().filter {
                it !== Resource.Loading("")
            }.collectLatest {
                try {
                    mutablePrediksi.value = Resource.Success(it.data!!)
                } catch (ex: Exception) {
                    mutablePrediksi.value = Resource.Error("")
                    ex.printStackTrace()
                }
            }

//            callGrafik.await().distinctUntilChanged().filter {
//                it !== Resource.Loading("")
//            }.collectLatest {
//                try {
//                    mutableGrafik.value = Resource.Success(it.data!!)
//                } catch (ex: Exception) {
//                    mutableGrafik.value = Resource.Error("")
//                    ex.printStackTrace()
//                }
//            }

            result(immutablePrediksi)

            _isRefreshing.emit(false)

//            grafikUseCase.getGrafik()
//                .distinctUntilChanged()
//                .collect {
//                    try {
//                        mutableGrafik.value = Resource.Success(it.data!!)
//                    } catch (ex: Exception) {
//                        //mutableGrafik.value = Resource.Error("")
//                        ex.printStackTrace()
//                    }
//                }
        }
    }
}