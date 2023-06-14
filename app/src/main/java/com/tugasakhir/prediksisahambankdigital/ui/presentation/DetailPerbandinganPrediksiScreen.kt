package com.tugasakhir.prediksisahambankdigital.ui.presentation

//import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import com.jaikeerthick.composable_graphs.color.*
import com.jaikeerthick.composable_graphs.composables.MultipleLineGraph
import com.jaikeerthick.composable_graphs.composables.SingleLineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.SahamItem
import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.DetailPrediksi
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.domain.model.Informasi
import com.tugasakhir.prediksisahambankdigital.ui.component.MultiSelector
import com.tugasakhir.prediksisahambankdigital.ui.component.PrediksiSahamList
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkGrey1
import com.tugasakhir.prediksisahambankdigital.ui.theme.DescriptionText
import com.tugasakhir.prediksisahambankdigital.ui.theme.SubTitleText
import com.tugasakhir.prediksisahambankdigital.ui.theme.TitleText
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar
import com.tugasakhir.prediksisahambankdigital.ui.util.checkConnectivityStatus
import com.tugasakhir.prediksisahambankdigital.ui.util.parseDayMonthYearFormat
import com.tugasakhir.prediksisahambankdigital.ui.util.roundDecimal
import com.tugasakhir.prediksisahambankdigital.viewmodel.DetailPerbandinganPrediksiViewModel
import com.tugasakhir.prediksisahambankdigital.viewmodel.DetailPerbandinganPrediksiViewModelFactory
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay

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
    val isDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    val sahamList = listOf(
        SahamItem("BBYB.JK", "Bank Neo Commerce", "bankneocommerce.co.id"),
        SahamItem("ARTO.JK", "Bank Jago", "jago.com"),
        SahamItem("BBHI.JK", "Allo Bank Indonesia", "allobank.com")
    )

    val key by rememberSaveable { mutableStateOf(kodeSaham) }

    var ukuran: Int? by rememberSaveable { mutableStateOf(0) }
    var grafikHistoriSahamList: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var grafikHistoriSahamListRaw: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var tanggalHistoriList: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var tanggalHistoriListRaw: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanHistoriList: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanHistoriListRaw: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }

    var hargaSahamSaatIni: Float by rememberSaveable { mutableStateOf(0.0F) }
    var tanggalPrediksiList: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var tanggalPrediksiListRaw: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanPrediksiList: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }

    var prediksiLSTMList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanPrediksiLSTMList: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanPrediksiLSTMListRaw: List<Float>? by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }
    var rmseLSTM: Float? by rememberSaveable { mutableStateOf(0.0F) }

    var prediksiGRUList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanPrediksiGRUList: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanPrediksiGRUListRaw: List<Float>? by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }
    var rmseGRU: Float? by rememberSaveable { mutableStateOf(0.0F) }

    val opsiPrediksi = mapOf(
        "LSTM" to 7,
        "GRU" to 7
    )
    val opsiListPrediksi = opsiPrediksi.toList()
    var selectedOpsiPrediksi by rememberSaveable { mutableStateOf(opsiListPrediksi[0]) }
    val clickedPrediksiPoin: MutableState<Pair<Any, Any>?> =
        rememberSaveable { mutableStateOf(null) }
    val clickedPrediksiOffset: MutableState<Offset?> = remember { mutableStateOf(null) }

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

    var isLoading: Boolean? by rememberSaveable { mutableStateOf(null) }
    var isError: Boolean? by rememberSaveable { mutableStateOf(null) }

    val opsiHistori = mapOf(
        "15D" to 15,
        "1M" to 30,
        "3M" to 90,
        "1Y" to 365,
        "3Y" to 1095,
        "Max" to 0
    )
    val opsiListHistori = opsiHistori.toList()
    var selectedOpsiHistori by rememberSaveable { mutableStateOf(opsiListHistori[5]) }
    val clickedHistoriPoin: MutableState<Pair<Any, Any>?> =
        rememberSaveable { mutableStateOf(null) }
    val clickedHistoriOffset: MutableState<Offset?> = remember { mutableStateOf(null) }

    val isOnline = checkConnectivityStatus()
    var isOnlinePage by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(isOnline) {
        isOnlinePage = if (isOnline) {
            delay(1000)

            true
        } else {
            false
        }
    }

    detailPerbandinganPrediksiViewModel.setKodeSaham(key)

    if (isOnlinePage) {
        LaunchedEffect(key1 = key) {
            detailPerbandinganPrediksiViewModel.getDetailPerbandinganPrediksi { prediksi, grafik, informasi ->
                prediksi.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                            isError = false
                        }
                        is Resource.Success -> {
                            isLoading = false
                            isError = false
                            hargaSahamSaatIni = it.data!!.hargaPenutupanSaatIni

                            prediksiLSTMList = it.data.prediksiLSTM
                            prediksiGRUList = it.data.prediksiGRU
                            rmseLSTM = it.data.rmseLSTM
                            rmseGRU = it.data.rmseGRU

                            val tanggal =
                                prediksiLSTMList!!.map { parseDayMonthYearFormat(it.tanggal) }
                                    .toTypedArray()
                                    .toList()
                            val hargaPenutupanLSTM =
                                prediksiLSTMList!!.map { roundDecimal(it.prediksiHargaPenutupan) }
                                    .toTypedArray().toList()
                            val hargaPenutupanGRU =
                                prediksiGRUList!!.map { roundDecimal(it.prediksiHargaPenutupan) }
                                    .toTypedArray().toList()

                            tanggalPrediksiList = tanggal.map { list ->
                                GraphData.String(list)
                            }
                            tanggalPrediksiListRaw = tanggal.map { list ->
                                GraphData.String(list)
                            }

                            hargaPenutupanPrediksiLSTMList = hargaPenutupanLSTM
                            hargaPenutupanPrediksiLSTMListRaw = hargaPenutupanLSTM
                            hargaPenutupanPrediksiGRUList = hargaPenutupanGRU
                            hargaPenutupanPrediksiGRUListRaw = hargaPenutupanGRU

                            hargaPenutupanPrediksiList = hargaPenutupanLSTM
                        }
                        is Resource.Error -> {
                            isLoading = false
                            isError = true
                        }
                    }
                }

                grafik.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                            isError = false
                        }
                        is Resource.Success -> {
                            isLoading = false
                            isError = false
                            ukuran = it.data?.size
                            grafikHistoriSahamList = it.data
                            grafikHistoriSahamListRaw = it.data

                            val tanggal =
                                it.data!!.map { parseDayMonthYearFormat(it.tanggal) }.toTypedArray()
                                    .toList()
                            val hargaPenutupan =
                                grafikHistoriSahamList!!.map { it.hargaPenutupan }
                                    .toTypedArray().toList()

                            tanggalHistoriList = tanggal.map { list ->
                                GraphData.String(list)
                            }
                            tanggalHistoriListRaw = tanggal.map { list ->
                                GraphData.String(list)
                            }
                            hargaPenutupanHistoriList = hargaPenutupan
                            hargaPenutupanHistoriListRaw = hargaPenutupan
                        }
                        is Resource.Error -> {
                            isLoading = false
                            isError = true
                        }
                    }
                }

                informasi.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                            isError = false
                        }
                        is Resource.Success -> {
                            isLoading = false
                            isError = false
                            informasiSaham = Informasi(
                                it.data!!.tentangPerusahaan,
                                it.data.sektor,
                                it.data.industri,
                                it.data.negara,
                                it.data.alamat,
                                it.data.jumlahPegawai,
                                it.data.tanggalDividenTerakhir
                            )
                        }
                        is Resource.Error -> {
                            isLoading = false
                            isError = true
                        }
                    }
                }
            }
        }

        Scaffold(
            topBar = { PageTopAppBar(navigateBack, key) }
        ) {
            it
            Surface(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(50.dp))

                    TitleText(modifier = Modifier, judul = "Prediksi Saham $kodeSaham")

                    Spacer(modifier = Modifier.height(50.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isLoading == false && isError == false) {
                            Text(
                                text = "Harga Saham Saat Ini",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.sp
                            )

                            Text(
                                text = hargaSahamSaatIni.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.sp
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .shimmer()
                                    .background(Color.Gray)
                                    .width(130.dp)
                                    .height(20.dp)
                                    .background(Color.Gray)
                            )

                            Box(
                                modifier = Modifier
                                    .shimmer()
                                    .background(Color.Gray)
                                    .width(65.dp)
                                    .height(20.dp)
                                    .background(Color.Gray)
                            )
                        }
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
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.sp
                        )

                        Text(
                            text = "Prediksi 2",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    if (isLoading == false && isError == false) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Metode: " + if (rmseLSTM!! <= rmseGRU!!) "LSTM" else "GRU",
                                fontSize = 15.sp,
                                color = DarkGrey1,
                                letterSpacing = 0.sp
                            )

                            Text(
                                text = "Metode: " + if (rmseLSTM!! > rmseGRU!!) "LSTM" else "GRU",
                                fontSize = 15.sp,
                                color = DarkGrey1,
                                letterSpacing = 0.sp
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Box(
                                modifier = Modifier
                                    .shimmer()
                                    .background(Color.Gray)
                                    .width(70.dp)
                                    .height(20.dp)
                                    .background(Color.Gray)
                            )

                            Box(
                                modifier = Modifier
                                    .shimmer()
                                    .background(Color.Gray)
                                    .width(70.dp)
                                    .height(20.dp)
                                    .background(Color.Gray)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    /**
                     * Detail Perbandingan Harga Close Saham
                     */
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
                                list = if (rmseLSTM!! <= rmseGRU!!) prediksiLSTMList!! else prediksiGRUList!!
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
                                list = if (rmseLSTM!! > rmseGRU!!) prediksiLSTMList!! else prediksiGRUList!!
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    /**
                     * Grafik Prediksi Saham
                     */
                    if (isLoading == false && isError == false) {
                        DetailPerbandinganPrediksiGrafikPrediksiSaham(
                            Modifier,
                            tanggalPrediksiList!!,
                            hargaPenutupanPrediksiList!!,
                            listOf(hargaPenutupanPrediksiLSTMList!!, hargaPenutupanPrediksiGRUList!!),
                            0,
                            selectedOpsiPrediksi.first,
                            clickedPrediksiPoin,
                            clickedPrediksiOffset,
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        MultiSelector(
                            options = opsiPrediksi,
                            optionsKey = opsiListPrediksi.map { it.first }.toTypedArray().toList(),
                            selectedOption = selectedOpsiPrediksi,
                            onOptionSelect = { opsi ->
                                selectedOpsiPrediksi = opsi
                                clickedPrediksiPoin.value = null
                                clickedPrediksiOffset.value = null
                                tanggalPrediksiList =
                                    if (selectedOpsiPrediksi.second > 0) tanggalPrediksiListRaw!!.takeLast(
                                        selectedOpsiPrediksi.second
                                    ) else tanggalPrediksiListRaw
                                hargaPenutupanPrediksiList =
                                    if (selectedOpsiPrediksi.first === "LSTM") hargaPenutupanPrediksiLSTMList!!.takeLast(
                                        selectedOpsiPrediksi.second
                                    ) else hargaPenutupanPrediksiGRUList!!.takeLast(
                                        selectedOpsiPrediksi.second
                                    )
                            },
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                    } else {
                        DetailPerbandinganPrediksiGrafikPrediksiSahamShimmer(Modifier)
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    /**
                     * Grafik Histori Saham
                     */
                    if (isLoading == false && isError == false) {
                        DetailPerbandinganPrediksiGrafikHistoriSaham(
                            Modifier,
                            tanggalHistoriList!!,
                            hargaPenutupanHistoriList!!,
                            selectedOpsiHistori.second,
                            clickedHistoriPoin,
                            clickedHistoriOffset
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        MultiSelector(
                            options = opsiHistori,
                            optionsKey = opsiListHistori.map { it.first }.toTypedArray().toList(),
                            selectedOption = selectedOpsiHistori,
                            onOptionSelect = { opsi ->
                                selectedOpsiHistori = opsi
                                clickedHistoriPoin.value = null
                                clickedHistoriOffset.value = null
                                tanggalHistoriList =
                                    if (selectedOpsiHistori.second > 0) tanggalHistoriListRaw!!.takeLast(
                                        selectedOpsiHistori.second
                                    ) else tanggalHistoriListRaw
                                hargaPenutupanHistoriList =
                                    if (selectedOpsiHistori.second > 0) hargaPenutupanHistoriListRaw!!.takeLast(
                                        selectedOpsiHistori.second
                                    ) else hargaPenutupanHistoriListRaw
                            },
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                    } else {
                        DetailPerbandinganPrediksiGrafikHistoriSahamShimmer(Modifier)
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    /**
                     * Informasi Saham
                     */
                    if (isLoading == false && isError == false) {
                        val kodeSahamList = sahamList.map { it.kode }.toTypedArray().toList()

                        DetailPerbandinganPrediksiTentangSaham(
                            Modifier,
                            sahamList[kodeSahamList.indexOf(key)],
                            informasiSaham
                        )
                    } else {
                        DetailPerbandinganPrediksiTentangSahamShimmer(Modifier)
                    }
                }
            }
        }
    } else {
        ErrorScreen(Modifier)
    }
}

@Composable
fun DetailPerbandinganPrediksiGrafikPrediksiSaham(
    modifier: Modifier,
    tanggalList: List<GraphData>,
    hargaPenutupanPrediksiList: List<Float>,
    hargaPenutupanPrediksiListList: List<List<Float>>,
    index: Int,
    selectedOpsi: String,
    clickedPoin: MutableState<Pair<Any, Any>?>,
    clickedOffset: MutableState<Offset?>
) {
    SubTitleText(modifier = modifier, judul = "Grafik Prediksi Saham")

    Spacer(modifier = modifier.height(30.dp))

    val lineGraphStyle = LineGraphStyle(
        paddingValues = PaddingValues(
            start = 15.dp,
            end = 15.dp
        ),
        height = 250.dp,
        visibility = LinearGraphVisibility(
            isHeaderVisible = false,
            isXAxisLabelVisible = true,
            isYAxisLabelVisible = true,
            isCrossHairVisible = true
        ),
        colors = LinearGraphColors(
            lineColor = LineGraphColor1(),
            pointColor = Color.Transparent,
            clickHighlightColor = PointHighlight2,
            fillGradient = Brush.verticalGradient(
                listOf(Gradient3, Gradient2)
            )
        )
    )

    /**
     * Menampilkan nilai dari poin yang diklik (x: tanggal, y: harga penutupan)
     */
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(15.dp),
            text = clickedPoin.value?.let { "(tanggal, harga penutupan): (${it.first}, ${it.second})" }
                ?: "Klik poin pada grafik untuk melihat nilai (tanggal, harga penutupan).",
            fontSize = 15.sp,
            lineHeight = 23.sp,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }

    Spacer(modifier = Modifier.height(5.dp))

    /**
     * Menampilkan grafik saham
     */
    MultipleLineGraph(
        xAxisData = tanggalList,
        yAxisData = hargaPenutupanPrediksiList,
        yAxisDataList = hargaPenutupanPrediksiListList,
        style = lineGraphStyle,
        index = if (index > 0) index else 0,
        selectedOpsi = selectedOpsi,
        onPointClicked = {
            clickedPoin.value = it
        },
        clickedOffset = clickedOffset
    )
}

@Composable
fun DetailPerbandinganPrediksiGrafikHistoriSaham(
    modifier: Modifier,
    tanggalHistoriList: List<GraphData>,
    hargaPenutupanHistoriList: List<Float>,
    index: Int,
    clickedPoin: MutableState<Pair<Any, Any>?>,
    clickedOffset: MutableState<Offset?>,
) {
    SubTitleText(modifier = modifier, judul = "Grafik Histori Saham")

    Spacer(modifier = modifier.height(30.dp))

    val lineGraphStyle = LineGraphStyle(
        paddingValues = PaddingValues(
            start = 15.dp,
            end = 15.dp
        ),
        height = 250.dp,
        visibility = LinearGraphVisibility(
            isHeaderVisible = false,
            isXAxisLabelVisible = true,
            isYAxisLabelVisible = true,
            isCrossHairVisible = true
        ),
        colors = LinearGraphColors(
            lineColor = LineGraphColor1(),
            pointColor = Color.Transparent,
            clickHighlightColor = PointHighlight2,
            fillGradient = Brush.verticalGradient(
                listOf(Gradient3, Gradient2)
            )
        )
    )

    /**
     * Menampilkan nilai dari poin yang diklik (x: tanggal, y: harga penutupan)
     */
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(15.dp),
            text = clickedPoin.value?.let { "(tanggal, harga penutupan): (${it.first}, ${it.second})" }
                ?: "Klik poin pada grafik untuk melihat nilai (tanggal, harga penutupan).",
            fontSize = 15.sp,
            lineHeight = 23.sp,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }

    Spacer(modifier = Modifier.height(5.dp))

    /**
     * Menampilkan grafik saham
     */
    SingleLineGraph(
        xAxisData = tanggalHistoriList,
        yAxisData = hargaPenutupanHistoriList,
        style = lineGraphStyle,
        index = if (index > 0) index else 0,
        onPointClicked = {
            clickedPoin.value = it
        },
        clickedOffset = clickedOffset
    )
}

@Composable
fun DetailPerbandinganPrediksiTentangSaham(
    modifier: Modifier,
    sahamItem: SahamItem,
    informasiSaham: Informasi
) {
    TitleText(modifier = Modifier, judul = "Tentang Saham")

    Spacer(modifier = Modifier.height(50.dp))

    Column {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://logo.clearbit.com/${sahamItem.url}"),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = sahamItem.nama,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        DescriptionText(modifier = Modifier, deskripsi = informasiSaham.tentangPerusahaan)

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.baseline_category_24),
                contentDescription = "Industri (Sektor)",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = informasiSaham.sektor + " (" + informasiSaham.industri + ")",
                fontSize = 15.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.baseline_location_on_24),
                contentDescription = "Alamat",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = informasiSaham.alamat,
                fontSize = 15.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "Jumlah Pegawai",
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "${informasiSaham.jumlahPegawai} pegawai",
                fontSize = 15.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun DetailPerbandinganPrediksiGrafikPrediksiSahamShimmer(modifier: Modifier) {
    TitleText(modifier = modifier, judul = "Grafik Prediksi Saham")

    Spacer(modifier = modifier.height(30.dp))

    Box(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .shimmer()
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    )
}

@Composable
fun DetailPerbandinganPrediksiGrafikHistoriSahamShimmer(modifier: Modifier) {
    TitleText(modifier = modifier, judul = "Grafik Histori Saham")

    Spacer(modifier = modifier.height(30.dp))

    Box(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .shimmer()
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    )
}

@Composable
fun DetailPerbandinganPrediksiTentangSahamShimmer(modifier: Modifier) {
    TitleText(modifier = modifier, judul = "Tentang Saham")

    Spacer(modifier = Modifier.height(30.dp))

    Column {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier
                    .shimmer()
                    .width(120.dp)
                    .height(20.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .shimmer()
                .width(180.dp)
                .height(15.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier
                    .shimmer()
                    .width(70.dp)
                    .height(15.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier
                    .shimmer()
                    .width(70.dp)
                    .height(15.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .shimmer()
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier
                    .shimmer()
                    .width(70.dp)
                    .height(15.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}