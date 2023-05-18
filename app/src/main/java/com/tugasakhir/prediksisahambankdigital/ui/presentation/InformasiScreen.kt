package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick
import com.tugasakhir.prediksisahambankdigital.ui.theme.TitleText

//private val informasiList =
//    mapOf(
//        "Umum" to listOf("Panduan Penggunaan", "Tentang Aplikasi"),
//        "Hubungi Kami!" to listOf("Kontak")
//    )

private val informasiList = listOf("Panduan Penggunaan", "Tentang Aplikasi", "Kontak")

@Composable
fun InformasiScreen(
    modifier: Modifier, navController: NavHostController = rememberNavController()
) {
    val onItemClick: OnItemClick = {
        when (it) {
            "Panduan Penggunaan" -> {
                navController.navigate(ScreenRoute.PanduanPenggunaan.createRoute())

                // context.startActivity(Intent(context, PanduanPenggunaanActivity::class.java))
            }
            "Tentang Aplikasi" -> {
                navController.navigate(ScreenRoute.TentangAplikasi.createRoute())
            }
            "Kontak" -> {
                navController.navigate(ScreenRoute.Kontak.createRoute())
            }
        }
    }

    Surface(modifier = modifier) {
        Column {
            Spacer(modifier = modifier.height(50.dp))

            TitleText(modifier = modifier, judul = "Informasi Aplikasi")

            Spacer(modifier = modifier.height(50.dp))

            InformasiAplikasiList(
                modifier = modifier,
                list = informasiList,
                onItemClick = onItemClick
            )
        }
    }
}