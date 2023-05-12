package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick

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

            Text(
                modifier = modifier.padding(start = 15.dp, end = 15.dp),
                text = "Informasi Aplikasi",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = modifier.height(50.dp))

            InformasiAplikasiList(
                modifier = modifier,
                list = informasiList,
                onItemClick = onItemClick
            )
        }
    }
}