package com.tugasakhir.prediksisahambankdigital

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

object Constants {
    val BottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "Beranda",
            icon = Icons.Default.Home,
            route = "beranda"
        ),
        BottomNavigationItem(
            label = "Informasi",
            icon = Icons.Default.Info, //Icons.Filled.Search,
            route = "informasi"
        )
    )
}