package com.tugasakhir.prediksisahambankdigital.ui.presentation

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import com.jaikeerthick.composable_graphs.color.*
import com.jaikeerthick.composable_graphs.composables.LineGraph
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
import com.tugasakhir.prediksisahambankdigital.ui.util.PageTopAppBar
import com.tugasakhir.prediksisahambankdigital.ui.util.checkConnectivityStatus
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

    // Up icon when expanded and down icon when collapsed
    val icon = if (isDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var ukuran: Int? by rememberSaveable { mutableStateOf(0) }
    var grafikSahamList: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var grafikSahamListRaw: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var tanggalList: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var tanggalListRaw: List<GraphData>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanList: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }
    var hargaPenutupanListRaw: List<Float>? by rememberSaveable { mutableStateOf(emptyList()) }

    var hargaSahamSaatIni: Float by rememberSaveable { mutableStateOf(0.0F) }
    var prediksiLSTMList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
//    var perbandinganPrediksiLSTMList: List<PerbandinganPrediksiItem>? by rememberSaveable {
//        mutableStateOf(
//            emptyList()
//        )
//    }
    val rmseLSTM: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var prediksiGRUList: List<DetailPrediksi>? by rememberSaveable { mutableStateOf(emptyList()) }
//    var perbandinganPrediksiGRUList: List<PerbandinganPrediksiItem>? by rememberSaveable {
//        mutableStateOf(
//            emptyList()
//        )
//    }
    val rmseGRU: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var informasiSaham: Informasi by rememberSaveable {
        mutableStateOf(
            Informasi(
                "",
                "",
                "",
                "",
                "",
                0,
                "4"
            )
        )
    }

    var isLoading: Boolean? by rememberSaveable { mutableStateOf(null) }

    val opsi = mapOf(
        "15D" to 15,
        "1M" to 30,
        "3M" to 90,
        "1Y" to 365,
        "3Y" to 1095,
        "Max" to 0
    )
    val opsiList = opsi.toList()
    var selectedOpsi by rememberSaveable { mutableStateOf(opsiList[5]) }
    val clickedPoin: MutableState<Pair<Any, Any>?> = rememberSaveable { mutableStateOf(null) }
    val clickedOffset: MutableState<Offset?> = remember { mutableStateOf(null) }

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

    if(isOnlinePage) {
        LaunchedEffect(key1 = key) {
            detailPerbandinganPrediksiViewModel.getDetailPerbandinganPrediksi { prediksi, grafik, informasi ->
                prediksi.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                        }
                        is Resource.Success -> {
                            isLoading = false
                            hargaSahamSaatIni = it.data!!.hargaPenutupanSaatIni
                            prediksiLSTMList = it.data!!.prediksiLSTM
                            prediksiGRUList = it.data.prediksiGRU
                        }
                        is Resource.Error -> {
                            isLoading = false
                        }
                    }
                }

                grafik.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                        }
                        is Resource.Success -> {
                            isLoading = false
                            ukuran = it.data?.size
                            grafikSahamList = it.data
                            grafikSahamListRaw = it.data

                            Log.d("Graphhhh", it.data.toString())

                            val tanggal = it.data!!.map { it.tanggal }.toTypedArray().toList()

                            tanggalList = tanggal.map { list ->
                                GraphData.String(list)
                            }
                            tanggalListRaw = tanggal.map { list ->
                                GraphData.String(list)
                            }
                            hargaPenutupanList =
                                grafikSahamList!!.map { it.hargaPenutupan }.toTypedArray().toList()
                            hargaPenutupanListRaw =
                                grafikSahamList!!.map { it.hargaPenutupan }.toTypedArray().toList()
                        }
                        is Resource.Error -> {
                            isLoading = false
                        }
                    }
                }

                informasi.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                        }
                        is Resource.Success -> {
                            isLoading = false
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
                        }
                    }
                }
            }
        }

//    for (i in prediksiGRUList!!.indices) {
//        var gambarKeterangan = 1
//
//        if (i > 0) {
//            gambarKeterangan =
//                if (prediksiLSTMList!![i].prediksiHargaPenutupan >= prediksiLSTMList!![i - 1].prediksiHargaPenutupan) 1 else 0
//        } else {
//            if (prediksiLSTMList!![i].prediksiHargaPenutupan >= hargaSahamSaatIni) 1 else 0
//        }
//    }
//
//    for (i in prediksiGRUList!!.indices) {
//        var gambarKeterangan = 1
//
//        if (i > 0) {
//            gambarKeterangan =
//                if (prediksiGRUList!![i].prediksiHargaPenutupan >= hargaSahamSaatIni) 1 else 0
//        }
//    }

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
                        if (isLoading == false) {
                            Text(
                                text = "Harga Saham Saat Ini",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Text(
                                text = hargaSahamSaatIni.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
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
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "Prediksi 2",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
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
                     * Grafik Saham
                     */
                    if (isLoading == false) {
                        DetailPerbandinganPrediksiGrafikSaham(
                            Modifier,
                            tanggalList!!,
                            hargaPenutupanList!!,
                            selectedOpsi.second,
                            clickedPoin,
                            clickedOffset
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        MultiSelector(
                            options = opsi,
                            optionsKey = opsiList.map { it.first }.toTypedArray().toList(),
                            selectedOption = selectedOpsi,
                            onOptionSelect = { opsi ->
                                selectedOpsi = opsi
                                clickedPoin.value = null
                                clickedOffset.value = null
                                tanggalList =
                                    if (selectedOpsi.second > 0) tanggalListRaw!!.takeLast(
                                        selectedOpsi.second
                                    ) else tanggalListRaw
                                hargaPenutupanList =
                                    if (selectedOpsi.second > 0) hargaPenutupanListRaw!!.takeLast(
                                        selectedOpsi.second
                                    ) else hargaPenutupanListRaw
                            },
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxWidth()
                                .height(56.dp)
                        )
                    } else {
                        DetailPerbandinganPrediksiGrafikSahamShimmer(Modifier)
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    /**
                     * Informasi Saham
                     */
                    if (isLoading == false) {
                        val kodeSahamList = sahamList!!.map { it.kode }.toTypedArray().toList()

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
    }
    else {
        ErrorScreen(Modifier)
    }
}

@Composable
fun DetailPerbandinganPrediksiGrafikSaham(
    modifier: Modifier,
    //grafikSahamList: List<Grafik>,
    tanggalList: List<GraphData>,
    hargaPenutupanList: List<Float>,
    index: Int,
    clickedPoin: MutableState<Pair<Any, Any>?>,
    clickedOffset: MutableState<Offset?>
) {
    Text(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        text = "Grafik Saham",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

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
            lineColor = GraphAccent2,
            pointColor = Color.Transparent,
            clickHighlightColor = PointHighlight2,
            fillGradient = Brush.verticalGradient(
                listOf(Gradient3, Gradient2)
            )
        )
    )

//    val tanggalList = grafikSahamList.map { it.tanggal }.toTypedArray().toList().map {
//        GraphData.String(it)
//    }
//    val hargaPenutupanList = grafikSahamList.map { it.hargaPenutupan }.toTypedArray().toList()

//    val xAxisData = if (index > 0) tanggalList.takeLast(index) else tanggalList
//    val yAxisData = if (index > 0) hargaPenutupanList.takeLast(index) else hargaPenutupanList

    /**
     * Menampilkan nilai dari poin yang diklik (x: tanggal, y: harga penutupan)
     */
    clickedPoin.value?.let {
        Row {
            Text(text = "Value: ", color = Color.Gray)
            Text(
                text = "${it.first}, ${it.second}",
                color = GraphAccent2,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    /**
     * Menampilkan grafik saham
     */
    LineGraph(
        xAxisData = tanggalList,
        yAxisData = hargaPenutupanList,
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
    Text(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        text = "Tentang Saham",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(30.dp))

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
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = informasiSaham.tentangPerusahaan,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp),
            fontSize = 15.sp,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = informasiSaham.sektor + " (" + informasiSaham.industri + ")",
                fontSize = 15.sp,
                lineHeight = 28.sp
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
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = informasiSaham.alamat,
                fontSize = 15.sp,
                lineHeight = 28.sp
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
                painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "${informasiSaham.jumlahPegawai} orang",
                fontSize = 15.sp,
                lineHeight = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun DetailPerbandinganPrediksiGrafikSahamShimmer(modifier: Modifier) {
    Text(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        text = "Grafik Saham",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

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
    Text(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        text = "Tentang Saham",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

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