package com.tugasakhir.prediksisahambankdigital.ui.presentation

sealed class ScreenRoute(val route: String) {
    object Beranda : ScreenRoute("beranda")
    object Informasi : ScreenRoute("informasi")
    object DetailPerbandinganPrediksi : ScreenRoute("beranda/detail_perbandingan_prediksi/{kodeSaham}") {
        fun createRoute(kodeSaham: String) = "beranda/detail_perbandingan_prediksi/$kodeSaham"
    }
    object PanduanPenggunaan : ScreenRoute("beranda/panduan_penggunaan") {
        fun createRoute() = "beranda/panduan_penggunaan"
    }

    object TentangAplikasi : ScreenRoute("beranda/tentang_aplikasi") {
        fun createRoute() = "beranda/tentang_aplikasi"
    }

    object TentangPengembang : ScreenRoute("beranda/tentang_aplikasi/tentang_pengembang") {
        fun createRoute() = "beranda/tentang_aplikasi/tentang_pengembang"
    }

    object Kontak : ScreenRoute("beranda/kontak") {
        fun createRoute() = "beranda/kontak"
    }
}