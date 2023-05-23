package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.repository.IPrediksiRepository

class PrediksiInteractor(private val iPrediksiRepository: IPrediksiRepository) : PrediksiUseCase {
    override suspend fun getPrediksi(kodeSaham: String) = iPrediksiRepository.getPrediksi(kodeSaham)
}