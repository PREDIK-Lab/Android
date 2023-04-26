package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar

private val informasiList =
    mapOf(
        "Lainnya" to listOf("Versi Saat ini", "Tentang Pengembang")
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

    Scaffold(
        topBar = { PageTopAppBar(navigateBack, "Tentang Aplikasi") }
    ) {
        it
        Surface(modifier = Modifier) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    text = "Aplikasi Prediksi ...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth(),
                    Alignment.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    text = "Aplikasi prediksi ...",
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(50.dp))

                InformasiAplikasiList(
                    modifier = Modifier,
                    list = informasiList,
                    onItemClick = onItemClick
                )
            }
        }
    }
}