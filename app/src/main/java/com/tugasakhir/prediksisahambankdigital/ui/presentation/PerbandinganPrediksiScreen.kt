package com.tugasakhir.prediksisahambankdigital.ui.presentation

import android.util.Log
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
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkBlue1
import com.tugasakhir.prediksisahambankdigital.viewmodel.PerbandinganPrediksiViewModel
import com.tugasakhir.prediksisahambankdigital.viewmodel.PerbandinganPrediksiViewModelFactory

private val perbandinganPrediksiList =
    listOf(
        PerbandinganPrediksiItem("Harga saham hari ini", 22.0F, R.drawable.up_arrow),
        PerbandinganPrediksiItem("Harga saham hari ini", 22.0F, R.drawable.down_arrow)
    )

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
        SahamItem("BBYB.JK", "Bank Neo Commerce", 1),
        SahamItem("ARTO.JK", "Bank Jago", 1),
        SahamItem("BBHI.JK", "Allo Bank Indonesia", 1)
    )

    var dropdownSelectedKode by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var dropdownSelectedNama by rememberSaveable { mutableStateOf(sahamList[0].nama) }
    var key by rememberSaveable { mutableStateOf(sahamList[0].kode) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up icon when expanded and down icon when collapsed
    val icon = if (isDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var rmseGRU: Float? by rememberSaveable { mutableStateOf(0.0F) }
    var ukuran: Int? by rememberSaveable { mutableStateOf(0) }
    var grafikSahamList: List<Grafik>? by rememberSaveable { mutableStateOf(emptyList()) }

    //perbandinganPrediksiViewModel.setKodeSaham(dropdownSelectedKode)

    LaunchedEffect(key1 = key) {
        perbandinganPrediksiViewModel.getPerbandinganPrediksi { prediksi, grafik ->
            prediksi.value.let {
                when (it) {
                    is Resource.Loading -> {
                        //Text("Loading")
                    }
                    is Resource.Success -> {
                        //Log.d("Grafik", "Ukuran: " + it.data?.rmseGRU.toString())
                        rmseGRU = it.data?.rmseGRU
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
                        Log.d("Grafik", "Ukuran: " + it.data?.size.toString())
                        ukuran = it.data?.size
                        grafikSahamList = it.data
                    }
                    is Resource.Error -> {

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
                // Create an Outlined Text Field
                // with icon and not expanded
                TextField(
                    value = dropdownSelectedNama,
                    onValueChange = { dropdownSelectedKode = it },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    singleLine = true,
                    label = null,
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
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
                            dropdownSelectedKode = label.kode
                            dropdownSelectedNama = label.nama
                            isDropdownExpanded = false
                        }) {
                            Text(text = label.kode)
                        }
                    }
                }
            }

            Spacer(modifier = modifier.height(30.dp))

            Button(
                onClick = {
                    perbandinganPrediksiViewModel.setKodeSaham(dropdownSelectedKode)

                    key = dropdownSelectedKode
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

//            PerbandinganPrediksiScreen(
//                modifier,
//                key,
//                grafikSahamList!!,
//                navigateToDetailPerbandinganPrediksi
//            )

            Row(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PerbandinganPrediksiBox(modifier.weight(1f), perbandinganPrediksiList[0])
                Spacer(Modifier.weight(0.1f))
                PerbandinganPrediksiBox(modifier.weight(1f), perbandinganPrediksiList[1])
            }

            Spacer(modifier = modifier.height(15.dp))

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

            Spacer(modifier = modifier.height(50.dp))

            PerbandinganPrediksiGrafikSaham(modifier, grafikSahamList!!)
        }
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
        chart = lineChart(),
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        startAxis = startAxis(),
        bottomAxis = bottomAxis(tickPosition = HorizontalAxis.TickPosition.Center(1, 20)),
        model = chartEntryModel,
    )
}