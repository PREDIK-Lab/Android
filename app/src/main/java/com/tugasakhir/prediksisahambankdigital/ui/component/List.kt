package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.domain.model.DetailPrediksi
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkBlue1
import com.tugasakhir.prediksisahambankdigital.ui.theme.LightGrey1
import com.tugasakhir.prediksisahambankdigital.ui.theme.LightGrey2

typealias OnItemClick = (String) -> Unit

@Composable
fun InformasiAplikasiList(
    modifier: Modifier,
    list: Map<String, List<String>>,
    onItemClick: OnItemClick
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(items = list.toList()) {
            Text(
                it.first,
                modifier = modifier
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = modifier.padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    (it.second).forEach {
                        InformasiAplikasiListItem(
                            modifier = modifier,
                            item = it,
                            onItemClick = onItemClick
                        )

                        Divider(
                            modifier = modifier.padding(start = 15.dp, end = 15.dp),
                            color = LightGrey2
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun InformasiAplikasiListItem(
    modifier: Modifier,
    item: String,
    onItemClick: OnItemClick
) {
    Row(modifier = modifier.clickable { onItemClick(item) }) {
        Text(
            item,
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            color = DarkBlue1,
        )

        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = LightGrey1,
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            contentDescription = item
        )
    }
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
                        item.tanggal,
                        modifier = modifier
                            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        item.prediksiHargaPenutupan.toString(),
                        modifier = modifier
                            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    Image(
                        painter = if ((index > 0 && item.prediksiHargaPenutupan >= list[index - 1].prediksiHargaPenutupan) || item.prediksiHargaPenutupan >= hargaSahamSaatIni) painterResource(
                            R.drawable.up_arrow
                        ) else painterResource(
                            R.drawable.down_arrow
                        ),
                        contentDescription = if ((index > 0 && item.prediksiHargaPenutupan >= list[index - 1].prediksiHargaPenutupan) || item.prediksiHargaPenutupan >= hargaSahamSaatIni) "Up" else "Down",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 15.dp),
                    )
                }

                Divider(
                    modifier = modifier.padding(start = 15.dp, end = 15.dp),
                    color = LightGrey2
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
            color = DarkBlue1,
            fontSize = 18.sp
        )
    }
}