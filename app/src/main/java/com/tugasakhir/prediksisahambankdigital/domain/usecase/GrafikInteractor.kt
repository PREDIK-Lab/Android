package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.repository.IGrafikRepository

class GrafikInteractor(private val iGrafikRepository: IGrafikRepository) : GrafikUseCase {
    override fun getGrafik(kodeSaham: String) = iGrafikRepository.getGrafik(kodeSaham)
}