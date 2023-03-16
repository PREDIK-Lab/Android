package com.tugasakhir.prediksisahambankdigital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tugasakhir.prediksisahambankdigital.domain.usecase.PrediksiUseCase

class PrediksiViewModel(private val prediksiUseCase: PrediksiUseCase) : ViewModel() {
    val prediksi = prediksiUseCase.getPrediksi("BBYB.JK").asLiveData()
}