package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick
import com.tugasakhir.prediksisahambankdigital.util.PageTopAppBar

private val informasiList =
    mapOf(
        "Lainnya" to listOf("Versi Saat ini", "Tentang Pengembang")
    )

@Composable
fun TentangAplikasiScreen(
    navigateBack: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val onItemClick: OnItemClick = {
        if (it == "Tentang Pengembang") {
            navController.navigate(ScreenRoute.TentangPengembang.createRoute())
        }
    }

    Scaffold(
        topBar = { PageTopAppBar(navigateBack) }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(text = "Aplikasi Prediksi ...", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = "Aplikasi Prediksi ...", fontSize = 15.sp)

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