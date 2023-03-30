package com.tugasakhir.prediksisahambankdigital.domain.util

import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.GrafikResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.InformasiResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.ListPrediksiResponse
import com.tugasakhir.prediksisahambankdigital.data.datasource.remote.PrediksiResponse
import com.tugasakhir.prediksisahambankdigital.domain.model.DetailPrediksi
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import com.tugasakhir.prediksisahambankdigital.domain.model.Prediksi
import kotlin.math.roundToInt

object DataMapper {
    fun mapResponseToPrediksi(input: List<ListPrediksiResponse>): List<Prediksi> {
//        input.map {
//            val prediksi = Prediksi(
//                [0.0],
//                it.hargaPenutupan
//            )
//            listPrediksi.add(prediksi)
//        }

        return ArrayList()
    }

    fun mapPrediksiResponseToPrediksi(input: PrediksiResponse): Prediksi {
        val listPrediksiLSTM = ArrayList<DetailPrediksi>()
        val listPrediksiGRU = ArrayList<DetailPrediksi>()

        input.hasilLSTM.prediksi.map {
            listPrediksiLSTM.add(
                DetailPrediksi(
                    it.prediksiHargaPenutupan,
                    it.tanggal
                )
            )
        }

        input.hasilGRU.prediksi.map {
            listPrediksiLSTM.add(
                DetailPrediksi(
                    it.prediksiHargaPenutupan,
                    it.tanggal
                )
            )
        }

        return Prediksi(
            input.hasilLSTM.hargaPenutupanSaatIni,
            input.hasilLSTM.hargaPenutupanSebelumnya,
            listPrediksiLSTM,
            input.hasilLSTM.rmse,
            listPrediksiGRU,
            input.hasilGRU.rmse,
        )
    }

    fun mapGrafikResponseToListGrafik(input: GrafikResponse): List<Grafik> {
        val listGrafik = ArrayList<Grafik>()

        input.grafik.map {
            val grafik = Grafik(
                it.tanggal,
                it.hargaPenutupan
            )
            listGrafik.add(grafik)
        }

        return listGrafik
    }

    fun mapInformasiResponseToInformasi(input: InformasiResponse): Informasi {
        return Informasi(
            input.tentangPerusahaan,
            input.sektor,
            input.industri,
            input.negara,
            input.alamat,
            input.jumlahPegawai,
            input.tanggalDividenTerakhir,
        )
    }
}
