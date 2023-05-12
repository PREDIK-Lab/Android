package com.tugasakhir.prediksisahambankdigital.ui.util

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Memanggil TopAppBar untuk indikasi kembali
 */
@Composable
fun PageTopAppBar(onBack: () -> Unit, title: String = "") {
    TopAppBar(
        modifier = Modifier.height(60.dp),
        title = {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 2.dp
    )
}