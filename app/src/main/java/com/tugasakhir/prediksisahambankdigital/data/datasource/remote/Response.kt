package com.tugasakhir.prediksisahambankdigital.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class ListPrediksiResponse(
    @field:SerializedName("harga_penutupan")
    val hargaPenutupan: Float,
)

data class PrediksiResponse(
    @field:SerializedName("hasil_lstm")
    val hasilLSTM: DetailPrediksiResponse,

    @field:SerializedName("hasil_gru")
    val hasilGRU: DetailPrediksiResponse,
)

data class DetailPrediksiResponse(
    @field:SerializedName("harga_penutupan_saat_ini")
    val hargaPenutupanSaatIni: Float,

    @field:SerializedName("harga_penutupan_sebelumnya")
    val hargaPenutupanSebelumnya: Float,

    @field:SerializedName("prediksi")
    val prediksi: List<DetailDetailPrediksiResponse>,

    @field:SerializedName("rmse")
    val rmse: Float,
)

data class DetailDetailPrediksiResponse(
    @field:SerializedName("prediksi_harga_penutupan")
    val prediksiHargaPenutupan: Float,

    @field:SerializedName("tanggal")
    val tanggal: String,
)

data class GrafikResponse(
    @field:SerializedName("grafik")
    val grafik: List<DetailGrafikResponse>,
)

data class DetailGrafikResponse(
    @field:SerializedName("tanggal")
    val tanggal: String,

    @field:SerializedName("harga_penutupan")
    val hargaPenutupan: Float,
)

data class InformasiResponse(
    @field:SerializedName("tentang_perusahaan")
    val tentangPerusahaan: String,

    @field:SerializedName("sektor")
    val sektor: String,

    @field:SerializedName("industri")
    val industri: String,

    @field:SerializedName("negara")
    val negara: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("jumlah_pegawai")
    val jumlahPegawai: Int,

    @field:SerializedName("tanggal_dividen_terakhir")
    val tanggalDividenTerakhir: String,
)