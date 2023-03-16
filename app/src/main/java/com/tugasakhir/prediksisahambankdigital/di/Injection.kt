package com.tugasakhir.prediksisahambankdigital.di

import android.content.Context
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.APIConfig
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.RemoteDataSource
import com.tugasakhir.prediksisahambankdigital.data.repository.*
import com.tugasakhir.prediksisahambankdigital.domain.usecase.*
import com.tugasakhir.prediksisahambankdigital.domain.util.AppExecutors

object Injection {
    private fun providePrediksiRepository(context: Context): IPrediksiRepository {
        val remoteDataSource = RemoteDataSource.getInstance(APIConfig.provideApiService())
        val appExecutors = AppExecutors()

        return PrediksiRepository.getInstance(remoteDataSource, appExecutors)
    }

    fun providePrediksiUseCase(context: Context): PrediksiUseCase {
        val repository = providePrediksiRepository(context)
        return PrediksiInteractor(repository)
    }

    private fun provideGrafikRepository(context: Context): IGrafikRepository {
        val remoteDataSource = RemoteDataSource.getInstance(APIConfig.provideApiService())
        val appExecutors = AppExecutors()

        return GrafikRepository.getInstance(remoteDataSource, appExecutors)
    }

    fun provideGrafikUseCase(context: Context): GrafikUseCase {
        val repository = provideGrafikRepository(context)
        return GrafikInteractor(repository)
    }

    private fun provideInformasiRepository(context: Context): IInformasiRepository {
        val remoteDataSource = RemoteDataSource.getInstance(APIConfig.provideApiService())
        val appExecutors = AppExecutors()

        return InformasiRepository.getInstance(remoteDataSource, appExecutors)
    }

    fun provideInformasiUseCase(context: Context): InformasiUseCase {
        val repository = provideInformasiRepository(context)
        return InformasiInteractor(repository)
    }
}