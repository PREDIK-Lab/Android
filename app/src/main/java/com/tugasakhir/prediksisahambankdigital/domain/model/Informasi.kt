package com.tugasakhir.prediksisahambankdigital.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Informasi(
    val tentangPerusahaan: String,
    val sektor: String,
    val industri: String,
    val negara: String,
    val alamat: String,
    val jumlahPegawai: Int,
    val tanggalDividenTerakhir: String,
) : Parcelable
