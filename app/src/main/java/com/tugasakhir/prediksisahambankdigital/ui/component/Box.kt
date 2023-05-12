package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.PerbandinganPrediksiItem
import com.tugasakhir.prediksisahambankdigital.ui.theme.NumberBoxText
import com.tugasakhir.prediksisahambankdigital.ui.theme.TitleBoxText
import com.valentinilk.shimmer.shimmer

@Composable
fun PerbandinganPrediksiBox(
    modifier: Modifier,
    perbandinganPrediksiItem: PerbandinganPrediksiItem,
    perbandinganPrediksiLainItem: PerbandinganPrediksiItem? = null
) {
    //val context = LocalContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleBoxText(modifier = Modifier, judul = perbandinganPrediksiItem.judul!!)

            Row(Modifier.weight(0.5f, false)) {
                NumberBoxText(modifier = Modifier, judul = perbandinganPrediksiItem.hargaPenutupan)

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(perbandinganPrediksiItem.gambarKeterangan),
                    contentDescription = "Up",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 15.dp),
                )
            }

            perbandinganPrediksiLainItem?.let {
                TitleBoxText(modifier = Modifier, judul = "Prediksi lainnya")

                Row(Modifier.weight(1f, false)) {
                    NumberBoxText(modifier = Modifier, judul = it.hargaPenutupan, 15.sp)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(perbandinganPrediksiItem.gambarKeterangan),
                        contentDescription = "Up",
                        modifier = Modifier
                            .size(38.dp)
                            .padding(end = 15.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun PerbandinganPrediksiBoxShimmer(
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp)
                    .shimmer()
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(23.dp)
                    .background(Color.Gray)
            )

            Row(Modifier.weight(1f, false)) {
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp)
                        .shimmer()
                        .size(40.dp)
                        .background(Color.Gray)
                )

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp)
                        .shimmer()
                        .size(40.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}