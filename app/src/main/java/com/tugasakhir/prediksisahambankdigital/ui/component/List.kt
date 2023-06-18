package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tugasakhir.prediksisahambankdigital.BuildConfig
import com.tugasakhir.prediksisahambankdigital.domain.model.DetailPrediksi
import com.tugasakhir.prediksisahambankdigital.ui.theme.*
import com.tugasakhir.prediksisahambankdigital.ui.util.parseDayMonthFormat
import com.tugasakhir.prediksisahambankdigital.ui.util.roundDecimal
import java.util.*

typealias OnItemClick = (String) -> Unit

@Composable
fun InformasiAplikasiList(
    modifier: Modifier,
    list: List<String>,
    onItemClick: OnItemClick
) {
    Card(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            (list).forEachIndexed { index, it ->
                InformasiAplikasiListItem(
                    modifier = modifier,
                    item = it,
                    onItemClick = onItemClick
                )

                if (index != list.size - 1)
                    Divider(
                        modifier = modifier.padding(start = 15.dp, end = 15.dp),
                        color = if (isSystemInDarkTheme()) DarkGrey1 else LightGrey2
                    )
            }
        }
    }
}

@Composable
fun InformasiAplikasiList(
    modifier: Modifier,
    list: Map<String, List<String>>,
    onItemClick: OnItemClick
) {
//    val listState = rememberLazyListState()

//    LazyColumn(state = listState) {
//        items(items = list.toList()) {
//
//        }
//    }

    (list.toList()).forEach {
        ListTitleText(modifier = modifier, judul = it.first)

        Card(
            modifier = modifier.padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column {
                (it.second).forEachIndexed { index, item ->
                    InformasiAplikasiListItem(
                        modifier = modifier,
                        item = item,
                        onItemClick = onItemClick
                    )

                    if (index != it.second.size - 1)
                        Divider(
                            modifier = modifier.padding(start = 15.dp, end = 15.dp),
                            color = if (isSystemInDarkTheme()) DarkGrey1 else LightGrey2
                        )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun InformasiAplikasiListItem(
    modifier: Modifier,
    item: String,
    onItemClick: OnItemClick
) {
    Row(modifier = modifier.clickable { onItemClick(item) }) {
        ListItemClickableText(modifier = modifier, judul = item)

        Spacer(Modifier.weight(1f))

        if (item.lowercase(Locale.getDefault()) == "versi saat ini") {
            ListItemText(modifier = modifier, judul = BuildConfig.VERSION_NAME)
        } else {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = LightGrey1,
                modifier = modifier
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                contentDescription = item
            )
        }
    }
}

@Composable
fun PanduanPenggunaanList(
    modifier: Modifier,
    list: Map<String, String>
) {
    val listState = rememberLazyListState()

//    LazyColumn(state = listState) {
    list.toList().forEachIndexed { index, it ->
        Card(
            modifier = modifier.padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(it.first),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .size(150.dp),
                    Alignment.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                DescriptionText(modifier = modifier, deskripsi = it.second)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun PrediksiSahamList(
    modifier: Modifier = Modifier,
    hargaSahamSaatIni: Float,
    list: List<DetailPrediksi>
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        itemsIndexed(list) { index, item ->
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = parseDayMonthFormat(item.tanggal),
                        modifier = modifier
                            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.sp
                    )

                    Spacer(Modifier.weight(1f))

                    //TitleBoxText(modifier = modifier, judul = item.prediksiHargaPenutupan.toString())

                    Text(
                        text = roundDecimal(item.prediksiHargaPenutupan).toString(),
                        modifier = modifier
                            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    )

                    Spacer(Modifier.weight(1f))

//                    Image(
//                        painter = if ((index > 0 && item.prediksiHargaPenutupan >= list[index - 1].prediksiHargaPenutupan) || item.prediksiHargaPenutupan >= hargaSahamSaatIni) painterResource(
//                            R.drawable.up_arrow
//                        ) else painterResource(
//                            R.drawable.down_arrow
//                        ),
//                        contentDescription = if ((index > 0 && item.prediksiHargaPenutupan >= list[index - 1].prediksiHargaPenutupan) || item.prediksiHargaPenutupan >= hargaSahamSaatIni) "Up" else "Down",
//                        modifier = Modifier
//                            .size(50.dp)
//                            .padding(end = 15.dp),
//                    )
                }

                if (index != list.size - 1)
                    Divider(
                        modifier = modifier.padding(start = 15.dp, end = 15.dp),
                        color = if (isSystemInDarkTheme()) DarkGrey1 else LightGrey2
                    )
            }
        }
    }
}

@Composable
private fun PrediksiSahamListItem(
    modifier: Modifier,
    item: Float
) {
    Row {
        Text(
            item.toString(),
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            fontSize = 15.sp
        )

        Spacer(Modifier.weight(1f))

        Text(
            item.toString(),
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )
    }
}