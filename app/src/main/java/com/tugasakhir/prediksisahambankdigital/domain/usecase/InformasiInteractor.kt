package com.tugasakhir.prediksisahambankdigital.domain.usecase

import com.tugasakhir.prediksisahambankdigital.data.repository.IInformasiRepository

class InformasiInteractor(private val iInformasiRepository: IInformasiRepository) : InformasiUseCase {
    override suspend fun getInformasi(kodeSaham: String) = iInformasiRepository.getInformasi(kodeSaham)
}