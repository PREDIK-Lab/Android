package com.tugasakhir.prediksisahambankdigital.util

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
import androidx.compose.ui.unit.dp

@Composable
fun PageTopAppBar(onBack: () -> Unit, title: String = "") {
    TopAppBar(
        modifier = Modifier.height(60.dp),
        title = {
            Text(
                text = "A"
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