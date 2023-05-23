package com.tugasakhir.prediksisahambankdigital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tugasakhir.prediksisahambankdigital.domain.usecase.GrafikUseCase

class GrafikViewModel(private val grafikUseCase: GrafikUseCase) : ViewModel() {
    //val grafik = grafikUseCase.getGrafik("BBYB.JK").asLiveData()
}