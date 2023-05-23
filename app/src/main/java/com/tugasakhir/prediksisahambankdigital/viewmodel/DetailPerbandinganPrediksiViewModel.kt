package com.tugasakhir.prediksisahambankdigital.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import com.tugasakhir.prediksisahambankdigital.domain.usecase.GrafikUseCase
import com.tugasakhir.prediksisahambankdigital.domain.usecase.InformasiUseCase
import com.tugasakhir.prediksisahambankdigital.domain.usecase.PrediksiUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailPerbandinganPrediksiViewModel(
    private val prediksiUseCase: PrediksiUseCase,
    private val grafikUseCase: GrafikUseCase,
    private val informasiUseCase: InformasiUseCase
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

    private val mutableInformasi: MutableStateFlow<Resource<Informasi>> =
        MutableStateFlow(Resource.Loading())
    val immutableInformasi: StateFlow<Resource<Informasi>>
        get() = mutableInformasi.asStateFlow()

    //val prediksi = prediksiUseCase.getPrediksi("").asLiveData()

    fun setKodeSaham(kodeSaham: String) {
        mutableKodeSaham.value = kodeSaham
    }

    fun getDetailPerbandinganPrediksi(result: (prediksi: StateFlow<Resource<Prediksi>>, grafik: StateFlow<Resource<List<Grafik>>>, informasi: StateFlow<Resource<Informasi>>) -> Unit) {
        viewModelScope.launch {
            val callPrediksi = async { prediksiUseCase.getPrediksi(mutableKodeSaham.value) }
            val callGrafik = async { grafikUseCase.getGrafik(mutableKodeSaham.value) }
            val callInformasi = async { informasiUseCase.getInformasi(mutableKodeSaham.value) }

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

            callGrafik.await().distinctUntilChanged().filter {
                it !== Resource.Loading("")
            }.collectLatest {
                try {
                    mutableGrafik.value = Resource.Success(it.data!!)
                } catch (ex: Exception) {
                    mutableGrafik.value = Resource.Error("")
                    ex.printStackTrace()
                }
            }

            callInformasi.await().distinctUntilChanged().filter {
                it !== Resource.Loading("")
            }.collectLatest {
                try {
                    mutableInformasi.value = Resource.Success(it.data!!)
                } catch (ex: Exception) {
                    mutableInformasi.value = Resource.Error("")
                    ex.printStackTrace()
                }
            }

            result(immutablePrediksi, immutableGrafik, immutableInformasi)

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