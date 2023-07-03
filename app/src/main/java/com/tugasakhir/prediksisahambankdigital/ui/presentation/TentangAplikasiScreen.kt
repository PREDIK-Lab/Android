package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick
import com.tugasakhir.prediksisahambankdigital.ui.theme.DescriptionText
import com.tugasakhir.prediksisahambankdigital.ui.theme.SubTitleText
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar

private val informasiList =
    mapOf(
        "Lainnya" to listOf("Versi Saat Ini", "Tentang Pengembang")
    )

@Composable
fun TentangAplikasiScreen(
    navigateBack: () -> Unit,
    navController: NavHostController
) {
    val onItemClick: OnItemClick = {
        if (it == "Tentang Pengembang") {
            navController.navigate(ScreenRoute.TentangPengembang.createRoute())
        }
    }

    val logo =
        if (isSystemInDarkTheme()) "https://tugas-akhir.portfoliobypgh.my.id/logo/white_logo.png" else "https://tugas-akhir.portfoliobypgh.my.id/logo/color_logo.png"

    Scaffold(
        topBar = { PageTopAppBar(navigateBack, "Tentang Aplikasi") }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                SubTitleText(modifier = Modifier, judul = "PREDIK")

                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = rememberAsyncImagePainter(logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .size(130.dp),
                    Alignment.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                DescriptionText(
                    modifier = Modifier,
                    deskripsi = "PREDIK merupakan aplikasi prediksi harga penutupan saham bank digital yang memiliki dua hasil perbandingan: hasil dengan metode Long Short-Term Memory (LSTM) dan Gated Recurrent Unit (GRU). Terdapat tiga bank digital yang digunakan di dalam aplikasi ini, yaitu Bank Neo Commerce, Bank Jago, dan Allo Bank Indonesia.\n\nSumber data saham yang digunakan berasal dari web finance.yahoo.com."
                )

                Spacer(modifier = Modifier.height(50.dp))

                InformasiAplikasiList(
                    modifier = Modifier,
                    list = informasiList,
                    onItemClick = onItemClick
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}