package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.tugasakhir.prediksisahambankdigital.ui.theme.DescriptionText
import com.tugasakhir.prediksisahambankdigital.ui.theme.SubTitleText
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar

@Composable
fun TentangPengembangScreen(
    navigateBack: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = { PageTopAppBar(navigateBack, "Tentang Pengembang") }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                SubTitleText(modifier = Modifier, judul = "Paulina Graciela Harmanto")

                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = rememberAsyncImagePainter("https://tugas-akhir.portfoliobypgh.my.id/profile/profile_photo.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .size(130.dp),
                    Alignment.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                DescriptionText(
                    modifier = Modifier,
                    deskripsi = "Seorang mahasiswa S1-Teknik Informatika dari Institut Informatika Indonesia Surabaya yang memiliki ketertarikan untuk mendalami pemrograman Android. Ia berhasil mendapatkan sertifikat Associate Android Developer pada November 2022, dan ingin terus berkembang lebih lagi. Sebagai Tugas Akhirnya, ia membuat aplikasi Android dinamakan \"PREDIK\" dengan bimbingan dari Timothy John Pattiasina dan Edwin Meinardi Trianto."
                )
            }
        }
    }
}