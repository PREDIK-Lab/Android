package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tugasakhir.prediksisahambankdigital.util.PageTopAppBar

@Composable
fun TentangPengembangScreen(navigateBack: () -> Unit) {
    Scaffold(
        topBar = { PageTopAppBar(navigateBack) }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "hs")
            }
        }
    }
}