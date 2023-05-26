package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.R

@Composable
fun ErrorScreen(modifier: Modifier) {
    Surface {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(15.dp), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painterResource(id = R.drawable.baseline_signal_cellular_connected_no_internet_0_bar_24),
                    modifier = Modifier.size(130.dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Maaf, koneksi internet Anda bermasalah...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Coba cek kembali jaringan Anda. Halaman ini akan memuat secara otomatis apabila terhubung kembali.",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp,
                    letterSpacing = 0.sp
                )
            }
        }
    }
}