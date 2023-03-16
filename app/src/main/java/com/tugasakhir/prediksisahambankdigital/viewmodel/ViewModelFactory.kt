package com.tugasakhir.prediksisahambankdigital.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tugasakhir.prediksisahambankdigital.di.Injection
import com.tugasakhir.prediksisahambankdigital.domain.usecase.GrafikUseCase
import com.tugasakhir.prediksisahambankdigital.domain.usecase.InformasiUseCase
import com.tugasakhir.prediksisahambankdigital.domain.usecase.PrediksiUseCase

class PerbandinganPrediksiViewModelFactory private constructor(
    private val prediksiUseCase: PrediksiUseCase,
    private val grafikUseCase: GrafikUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: PerbandinganPrediksiViewModelFactory? = null

        fun getInstance(context: Context): PerbandinganPrediksiViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PerbandinganPrediksiViewModelFactory(
                    Injection.providePrediksiUseCase(context),
                    Injection.provideGrafikUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(PerbandinganPrediksiViewModel::class.java) -> {
                PerbandinganPrediksiViewModel(prediksiUseCase, grafikUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}

class DetailPerbandinganPrediksiViewModelFactory private constructor(
    private val prediksiUseCase: PrediksiUseCase,
    private val grafikUseCase: GrafikUseCase,
    private val informasiUseCase: InformasiUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: DetailPerbandinganPrediksiViewModelFactory? = null

        fun getInstance(context: Context): DetailPerbandinganPrediksiViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailPerbandinganPrediksiViewModelFactory(
                    Injection.providePrediksiUseCase(context),
                    Injection.provideGrafikUseCase(context),
                    Injection.provideInformasiUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DetailPerbandinganPrediksiViewModel::class.java) -> {
                DetailPerbandinganPrediksiViewModel(prediksiUseCase, grafikUseCase, informasiUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}

class PrediksiViewModelFactory private constructor(private val prediksiUseCase: PrediksiUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: PrediksiViewModelFactory? = null

        fun getInstance(context: Context): PrediksiViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PrediksiViewModelFactory(
                    Injection.providePrediksiUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(PrediksiViewModel::class.java) -> {
                PrediksiViewModel(prediksiUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}

class GrafikViewModelFactory private constructor(private val grafikUseCase: GrafikUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: GrafikViewModelFactory? = null

        fun getInstance(context: Context): GrafikViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: GrafikViewModelFactory(
                    Injection.provideGrafikUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(GrafikViewModel::class.java) -> {
                GrafikViewModel(grafikUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}

//class InformasiViewModelFactory private constructor(private val grafikUseCase: InformasiUseCase) :
//    ViewModelProvider.NewInstanceFactory() {
//
//    companion object {
//        @Volatile
//        private var instance: InformasiViewModelFactory? = null
//
//        fun getInstance(context: Context): InformasiViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: InformasiViewModelFactory(
//                    Injection.provideInformasiUseCase(context)
//                )
//            }
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when {
//            modelClass.isAssignableFrom(InformasiViewModel::class.java) -> {
//                InformasiViewModel(grafikUseCase) as T
//            }
//            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
//        }
//}