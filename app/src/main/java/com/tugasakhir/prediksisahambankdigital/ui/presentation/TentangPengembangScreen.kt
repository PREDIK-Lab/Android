package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.util.PageTopAppBar

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

                Text(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    text = "Nama Pengembang",
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
                    text = "Profil pengembang ...",
                    fontSize = 15.sp
                )
            }
        }
    }
}