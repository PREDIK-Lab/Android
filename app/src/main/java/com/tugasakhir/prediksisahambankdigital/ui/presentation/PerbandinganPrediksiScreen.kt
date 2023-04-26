package com.tugasakhir.prediksisahambankdigital.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.tugasakhir.prediksisahambankdigital.PerbandinganPrediksiItem
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.SahamItem
import com.tugasakhir.prediksisahambankdigital.data.Resource
import com.tugasakhir.prediksisahambankdigital.domain.model.Grafik
import com.tugasakhir.prediksisahambankdigital.ui.component.PerbandinganPrediksiBox
import com.tugasakhir.prediksisahambankdigital.ui.component.PerbandinganPrediksiBoxShimmer
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkBlue1
import com.tugasakhir.prediksisahambankdigital.ui.util.checkConnectivityStatus
import com.tugasakhir.prediksisahambankdigital.viewmodel.PerbandinganPrediksiViewModel
import com.tugasakhir.prediksisahambankdigital.viewmodel.PerbandinganPrediksiViewModelFactory
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun PerbandinganPrediksiScreen(
    modifier: Modifier,
    navigateToDetailPerbandinganPrediksi: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val factory = PerbandinganPrediksiViewModelFactory.getInstance(context)
    val perbandinganPrediksiViewModel =
        ViewModelProvider(viewModelStoreOwner, factory)[PerbandinganPrediksiViewModel::class.java]
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val sahamList = listOf(
        SahamItem("BBYB.JK", "Bank Neo Commerce", "bankneocommerce.co.id"),
        SahamItem("ARTO.JK", "Bank Jago", "jago.com"),
        SahamItem("BBHI.JK", "Allo Bank Indonesia", "allobank.com")
    )

    var dropdownSelectedKodeSaham by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var dropdownSelectedNamaSaham by rememberSaveable { mutableStateOf(sahamList[0].nama) }
    var dropdownSelectedUrlSaham by rememberSaveable { mutableStateOf(sahamList[0].url) }

    var key by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Ikon "up" muncul ketika di-expand, namun ikon "down" muncul saat collapse
    val icon = if (isDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var hargaPenutupanSaatIni: Float? by rememberSaveable { mutableStateOf(0.0F) }
    val hargaPenutupanSebelumnya: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var hargaPenutupanBesok: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var rmseLSTM: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var rmseGRU: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var ukuran: Int? by rememberSaveable { mutableStateOf(0) }
    var grafikSahamList: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }
    var isLoading: Boolean? by rememberSaveable { mutableStateOf(null) }

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

    if (isOnlinePage) {
        LaunchedEffect(key1 = key) {
            isLoading = true

            perbandinganPrediksiViewModel.getPerbandinganPrediksi { prediksi, grafik ->
                prediksi.value.let {
                    when (it) {
                        is Resource.Loading -> {
                            isLoading = true
                        }
                        is Resource.Success -> {
                            isLoading = false
                            rmseLSTM = it.data?.rmseLSTM
                            rmseGRU = it.data?.rmseGRU
                            hargaPenutupanSaatIni = it.data?.hargaPenutupanSaatIni
                            hargaPenutupanBesok = it.data!!.prediksiLSTM[0].prediksiHargaPenutupan
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
                        }
                        is Resource.Error -> {
                            isLoading = false

                        }
                    }
                }
            }
        }

        Surface(modifier = modifier.verticalScroll(rememberScrollState())) {
            Column {
                Spacer(modifier = modifier.height(50.dp))

                Text(
                    modifier = modifier.padding(start = 15.dp, end = 15.dp),
                    text = "Perbandingan Prediksi",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = modifier.height(50.dp))

                Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                    // Create an Outlined Text Field with icon and not expanded
                    TextField(
                        value = "$dropdownSelectedNamaSaham (${dropdownSelectedKodeSaham})",
                        onValueChange = { dropdownSelectedKodeSaham = it },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to the DropDown the same width
                                textFieldSize = coordinates.size.toSize()
                            },
                        singleLine = true,
                        label = null,
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White,
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { isDropdownExpanded = !isDropdownExpanded })
                        }
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    ) {
                        sahamList.forEach { label ->
                            DropdownMenuItem(onClick = {
                                dropdownSelectedKodeSaham = label.kode
                                dropdownSelectedNamaSaham = label.nama
                                dropdownSelectedUrlSaham = label.url

                                isDropdownExpanded = false
                            }) {
                                Text(text = "${label.nama} (${label.kode})")
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(30.dp))

                Button(
                    onClick = {
                        perbandinganPrediksiViewModel.setKodeSaham(dropdownSelectedKodeSaham)

                        key = dropdownSelectedKodeSaham
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue1)
                ) {
                    Text(text = "Prediksi", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = modifier.height(50.dp))

                Row(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isLoading == false) {
                        PerbandinganPrediksiBox(
                            modifier
                                .weight(1f)
                                .height(150.dp),
                            PerbandinganPrediksiItem(
                                "Harga penutupan saham hari ini",
                                hargaPenutupanSaatIni!!.roundToInt().toFloat() / 10000,
                                if (hargaPenutupanSaatIni!! >= hargaPenutupanSebelumnya!!) R.drawable.up_arrow else R.drawable.down_arrow
                            )
                        )
                    } else {
                        PerbandinganPrediksiBoxShimmer(
                            modifier
                                .weight(1f)
                                .height(150.dp)
                        )
                    }

                    Spacer(Modifier.weight(0.1f))

                    if (isLoading == false) {
                        PerbandinganPrediksiBox(
                            modifier
                                .weight(1f)
                                .height(150.dp),
                            PerbandinganPrediksiItem(
                                "Prediksi harga penutupan saham besok",
                                hargaPenutupanBesok!!.roundToInt().toFloat() / 10000,
                                if (hargaPenutupanBesok!! >= hargaPenutupanSaatIni!!) R.drawable.up_arrow else R.drawable.down_arrow
                            )
                        )
                    } else {
                        PerbandinganPrediksiBoxShimmer(
                            modifier
                                .weight(1f)
                                .height(150.dp)
                        )
                    }
                }

                Spacer(modifier = modifier.height(15.dp))

                if (isLoading == false) {
                    ClickableText(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                        text = AnnotatedString("Selengkapnya..."),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue1,
                        ),
                        onClick = {
                            navigateToDetailPerbandinganPrediksi(key)
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp)
                            .shimmer()
                            .width(100.dp)
                            .height(15.dp)
                            .background(Color.Gray)
                    )
                }

                Spacer(modifier = modifier.height(50.dp))

                if (isLoading == false) {
                    PerbandinganPrediksiGrafikSaham(modifier, grafikSahamList!!)
                } else {
                    PerbandinganPrediksiGrafikSahamShimmer(modifier)
                }
            }
        }
    } else {
        ErrorScreen(modifier)
    }
}

@Composable
fun PerbandinganPrediksiGrafikSaham(modifier: Modifier, grafikSahamList: List<Grafik>) {
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
        chart = lineChart(spacing = 0.0.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        startAxis = startAxis(),
        bottomAxis = bottomAxis(
            tickPosition = HorizontalAxis.TickPosition.Center(1, 20),
            //sizeConstraint = Axis.SizeConstraint.Exact(1500f)
        ),
        isZoomEnabled = true,
        model = chartEntryModel
    )
}

@Composable
fun PerbandinganPrediksiGrafikSahamShimmer(modifier: Modifier) {
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