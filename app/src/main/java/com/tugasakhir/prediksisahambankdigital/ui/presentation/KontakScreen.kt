package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.ui.component.InformasiAplikasiList
import com.tugasakhir.prediksisahambankdigital.ui.component.OnItemClick
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar

private val kontakList =
    mapOf(
        "Media Sosial Pengembang" to listOf("E-mail", "GitHub", "LinkedIn")
    )

@Composable
fun KontakScreen(
    navigateBack: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val uriHandler = LocalUriHandler.current
    val onItemClick: OnItemClick = {
        val uri = when (it) {
            "E-mail" -> "mailto:mail@portfoliobypgh.my.id"
            "GitHub" -> "https://github.com/paulinagh"
            "LinkedIn" -> "https://www.linkedin.com/in/paulinagh/"
            else -> ""
        }

        uriHandler.openUri(uri)
    }

    Scaffold(
        topBar = { PageTopAppBar(navigateBack, "Kontak") }
    ) {
        it
        Surface(modifier = Modifier) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                InformasiAplikasiList(
                    modifier = Modifier,
                    list = kontakList,
                    onItemClick = onItemClick
                )
            }
        }
    }
}