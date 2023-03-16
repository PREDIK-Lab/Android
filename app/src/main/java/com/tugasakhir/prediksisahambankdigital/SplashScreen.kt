package com.tugasakhir.prediksisahambankdigital

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkBlue1
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000
private const val logo = R.drawable.logo_in_white

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeOut: () -> Unit) {
    Surface(
        color = DarkBlue1,
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
                    painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
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
            Text(text = "v.1.0", color = Color.White, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SplashScreenTextAddition(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize(),
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
                fontSize = 13.sp
            )

            Text(
                text = "Paulina Graciela Harmanto",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
