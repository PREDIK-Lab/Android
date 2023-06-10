package com.tugasakhir.prediksisahambankdigital.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

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

    fun setKodeSaham(kodeSaham: String) {
        mutableKodeSaham.value = kodeSaham
    }

    suspend fun getDetailPerbandinganPrediksi(result: (prediksi: StateFlow<Resource<Prediksi>>, grafik: StateFlow<Resource<List<Grafik>>>, informasi: StateFlow<Resource<Informasi>>) -> Unit) {
        viewModelScope.launch {
            val callPrediksi = prediksiUseCase.getPrediksi(mutableKodeSaham.value)
            val callGrafik = grafikUseCase.getGrafik(mutableKodeSaham.value)
            val callInformasi = informasiUseCase.getInformasi(mutableKodeSaham.value)

            val time = measureTimeMillis {
                callPrediksi.zip(callGrafik) { prediksi, grafik ->
                    Pair(prediksi, grafik)
                }.zip(callInformasi) { pair, informasi ->
                    Pair(pair, informasi)
                }.distinctUntilChanged().filter {
                    it !== null
                }.collectLatest { pair ->
                    try {
                        mutablePrediksi.value =
                            Resource.Success(pair.first.first.data!!)
                        mutableGrafik.value = Resource.Success(pair.first.second.data!!)
                        mutableInformasi.value = Resource.Success(pair.second.data!!)
                    } catch (ex: Exception) {
                        mutablePrediksi.value = Resource.Error("")
                        mutableGrafik.value = Resource.Error("")
                        mutableInformasi.value = Resource.Error("")

                        ex.printStackTrace()
                    }
                }
            }

            Log.d("async-await", "Time Taken To Complete-> $time ms.")

//            val callPrediksi = async(Dispatchers.IO) { prediksiUseCase.getPrediksi(mutableKodeSaham.value) }
//            val callGrafik = async(Dispatchers.IO) { grafikUseCase.getGrafik(mutableKodeSaham.value) }
//            val callInformasi = async(Dispatchers.IO) { informasiUseCase.getInformasi(mutableKodeSaham.value) }

//            val time = measureTimeMillis {
//                callPrediksi.await().distinctUntilChanged().filter {
//                    it !== Resource.Loading("")
//                }.collectLatest {
//                    try {
//                        mutablePrediksi.value = Resource.Success(it.data!!)
//                    } catch (ex: Exception) {
//                        mutablePrediksi.value = Resource.Error("")
//                        ex.printStackTrace()
//                    }
//                }
//
//                callGrafik.await().distinctUntilChanged().filter {
//                    it !== Resource.Loading("")
//                }.collectLatest {
//                    try {
//                        mutableGrafik.value = Resource.Success(it.data!!)
//                    } catch (ex: Exception) {
//                        mutableGrafik.value = Resource.Error("")
//                        ex.printStackTrace()
//                    }
//                }
//
//                callInformasi.await().distinctUntilChanged().filter {
//                    it !== Resource.Loading("")
//                }.collectLatest {
//                    try {
//                        mutableInformasi.value = Resource.Success(it.data!!)
//                    } catch (ex: Exception) {
//                        mutableInformasi.value = Resource.Error("")
//                        ex.printStackTrace()
//                    }
//                }

            result(immutablePrediksi, immutableGrafik, immutableInformasi)
        }

        //Log.d("async-await", "Time Taken To Complete-> $time ms.")
    }
}
