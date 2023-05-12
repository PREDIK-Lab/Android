package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tugasakhir.prediksisahambankdigital.ui.component.PanduanPenggunaanList
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar

private val panduanPenggunaanList =
    mapOf(
        "https://tugas-akhir.portfoliobypgh.my.id/image/1.png" to "Halaman beranda memiliki tiga fitur:\n1. Memilih saham bank digital untuk diprediksi.\n2. Menampilkan hasil prediksi harga penutupan saham bank digital tersebut.",
        "https://tugas-akhir.portfoliobypgh.my.id/image/2a.png" to "Pada halaman perbandingan prediksi, fitur ini bertujuan untuk menampilkan perbandingan hasil prediksi harga penutupan bank digital yang dipilih sebelumnya.",
        "https://tugas-akhir.portfoliobypgh.my.id/image/2b.png" to "Pada halaman perbandingan prediksi, fitur ini bertujuan untuk menampilkan grafik histori harga penutupan saham tersebut. Opsi di bawah bertujuan untuk menampilkan durasi grafik.",
        "https://tugas-akhir.portfoliobypgh.my.id/image/2c.png" to "Pada halaman perbandingan prediksi, fitur ini bertujuan untuk menampilkan profil informasi saham bank digital tersebut yang terdiri dari gambar ikon, nama perusahaan, ringkasan, dan sebagainya.",
        "https://tugas-akhir.portfoliobypgh.my.id/image/3.png" to "Halaman informasi akan menampilkan menu-menu seputar aplikasi dan pengembangnya."
    )

@Composable
fun PanduanPenggunaanScreen(navigateBack: () -> Unit) {
    Scaffold(
        topBar = { PageTopAppBar(navigateBack, "Panduan Penggunaan") }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column {
                Spacer(modifier = Modifier.height(50.dp))

                PanduanPenggunaanList(
                    modifier = Modifier,
                    list = panduanPenggunaanList
                )


            }
        }
    }
}