package com.tugasakhir.prediksisahambankdigital.ui.util

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
