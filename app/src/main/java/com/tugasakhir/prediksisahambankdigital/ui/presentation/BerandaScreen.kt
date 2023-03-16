package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BerandaScreen(modifier: Modifier, navigateToDetailPerbandinganPrediksi: (String) -> Unit) {
    PerbandinganPrediksiScreen(
        modifier = modifier,
        navigateToDetailPerbandinganPrediksi = navigateToDetailPerbandinganPrediksi
    )
}