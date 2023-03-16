package com.tugasakhir.prediksisahambankdigital

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

object Constants {
    val BottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "Beranda",
            icon = Icons.Default.AccountCircle,
            route = "beranda"
        ),
        BottomNavigationItem(
            label = "Informasi",
            icon = Icons.Default.AccountCircle, //Icons.Filled.Search,
            route = "informasi"
        )
    )
}