package com.tugasakhir.prediksisahambankdigital

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000
private const val logo = R.drawable.logo_in_white

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeOut: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(15.dp), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val currentOnTimeout by rememberUpdatedState(onTimeOut)

                LaunchedEffect(onTimeOut) {
                    delay(SplashWaitTime)
                    currentOnTimeout()
                }

                Image(
                    painter = rememberAsyncImagePainter("https://tugas-akhir.portfoliobypgh.my.id/logo/white_logo.png"),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
            }

            SplashScreenTextVersion(modifier)
            SplashScreenTextAddition(modifier)
        }
    }
}

@Composable
fun SplashScreenTextVersion(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(modifier = modifier.padding(bottom = 120.dp)) {
            Text(
                text = BuildConfig.VERSION_NAME,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(letterSpacing = 0.5.sp)
            )
        }
    }
}

@Composable
fun SplashScreenTextAddition(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painterResource(id = logo),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 40.dp)
        )

        Column(modifier = modifier.padding(bottom = 40.dp)) {
            Text(
                text = "Tugas Akhir oleh",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 13.sp,
                letterSpacing = 0.5.sp
            )

            Text(
                text = "Paulina Graciela Harmanto",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            )
        }
    }
}
