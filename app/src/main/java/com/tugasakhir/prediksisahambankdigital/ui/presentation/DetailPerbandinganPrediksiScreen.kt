package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.tugasakhir.prediksisahambankdigital.PerbandinganPrediksiItem
import com.tugasakhir.prediksisahambankdigital.SahamItem
import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.DetailPrediksi
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import com.tugasakhir.prediksisahambankdigital.ui.component.PrediksiSahamList
import com.tugasakhir.prediksisahambankdigital.util.PageTopAppBar
import com.tugasakhir.prediksisahambankdigital.viewmodel.DetailPerbandinganPrediksiViewModel
import com.tugasakhir.prediksisahambankdigital.viewmodel.DetailPerbandinganPrediksiViewModelFactory

@Composable
fun DetailPerbandinganPrediksiScreen(
    kodeSaham: String,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val factory = DetailPerbandinganPrediksiViewModelFactory.getInstance(context)
    val detailPerbandinganPrediksiViewModel =
        ViewModelProvider(
            viewModelStoreOwner,
            factory
        )[DetailPerbandinganPrediksiViewModel::class.java]
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val sahamList = listOf(
        SahamItem("BBYB.JK", "Bank Neo Commerce", 1),
        SahamItem("ARTO.JK", "sm", 1),
        SahamItem("BBHI.JK", "sm", 1)
    )

    var dropdownSelectedText by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var key by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up icon when expanded and down icon when collapsed
    val icon = if (isDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var ukuran: Int? by rememberSaveable { mutableStateOf(0) }
    var grafikSahamList: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaSahamSaatIni: Float by rememberSaveable { mutableStateOf(0.0F) }
    var prediksiLSTMList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
    var perbandinganPrediksiLSTMList: List<PerbandinganPrediksiItem>? by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }
    var rmseLSTM: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var prediksiGRUList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
    var perbandinganPrediksiGRUList: List<PerbandinganPrediksiItem>? by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }
    var rmseGRU: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var informasiSaham: Informasi by rememberSaveable {
        mutableStateOf(
            Informasi(
                "",
                "",
                "",
                "",
                "",
                0,
                ""
            )
        )
    }

    detailPerbandinganPrediksiViewModel.setKodeSaham(key)

    LaunchedEffect(key1 = key) {
        detailPerbandinganPrediksiViewModel.getDetailPerbandinganPrediksi { prediksi, grafik, informasi ->
            prediksi.value.let {
                when (it) {
                    is Resource.Loading -> {
                        //Text("Loading")
                    }
                    is Resource.Success -> {
                        prediksiLSTMList = it.data!!.prediksiLSTM
                        prediksiGRUList = it.data!!.prediksiGRU
                    }
                    is Resource.Error -> {

                    }
                }
            }

            grafik.value.let {
                when (it) {
                    is Resource.Loading -> {
                        //Text("Loading")
                    }
                    is Resource.Success -> {
                        ukuran = it.data?.size
                        grafikSahamList = it.data
                    }
                    is Resource.Error -> {

                    }
                }
            }

            informasi.value.let {
                when (it) {
                    is Resource.Loading -> {
                        //Text("Loading")
                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    for (i in prediksiGRUList!!.indices) {
        var gambarKeterangan = 1

        if (i > 0) {
            gambarKeterangan =
                if (prediksiLSTMList!![i].prediksiHargaPenutupan >= prediksiLSTMList!![i - 1].prediksiHargaPenutupan) 1 else 0
        } else {
            if (prediksiLSTMList!![i].prediksiHargaPenutupan >= hargaSahamSaatIni) 1 else 0
        }
    }

    for (i in prediksiGRUList!!.indices) {
        var gambarKeterangan = 1

        if (i > 0) {
            gambarKeterangan =
                if (prediksiGRUList!![i].prediksiHargaPenutupan >= hargaSahamSaatIni) 1 else 0
        }
    }

    Scaffold(
        topBar = { PageTopAppBar(navigateBack) }
    ) {
        it
        Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    text = "Prediksi Saham $kodeSaham",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Harga Saham Saat Ini",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "a",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Prediksi 1",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Prediksi 2",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(300.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        PrediksiSahamList(
                            hargaSahamSaatIni = hargaSahamSaatIni,
                            list = prediksiLSTMList!!
                        )
                    }

                    Spacer(Modifier.weight(0.1f))

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(300.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        PrediksiSahamList(
                            hargaSahamSaatIni = hargaSahamSaatIni,
                            list = prediksiGRUList!!
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                DetailPerbandinganPrediksiGrafikSaham(Modifier, grafikSahamList!!)

                Spacer(modifier = Modifier.height(50.dp))

                DetailPerbandinganPrediksiTentangSaham(Modifier)
            }
        }
    }
}

@Composable
fun DetailPerbandinganPrediksiGrafikSaham(modifier: Modifier, grafikSahamList: List<Grafik>) {
    Text(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        text = "Grafik Saham",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = modifier.height(30.dp))

    val hargaPenutupanList = grafikSahamList.map { it.hargaPenutupan }.toTypedArray()

    val chartEntryModel = entryModelOf(*hargaPenutupanList)

    Chart(
        chart = lineChart(spacing = 1.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        startAxis = startAxis(maxLabelCount = 70),
        bottomAxis = bottomAxis(
            tickPosition = HorizontalAxis.TickPosition.Center(1, 70),
            sizeConstraint = Axis.SizeConstraint.Auto()
        ),
        model = chartEntryModel,
    )
}

@Composable
fun DetailPerbandinganPrediksiTentangSaham(modifier: Modifier) {
    Text(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        text = "Tentang Saham",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}