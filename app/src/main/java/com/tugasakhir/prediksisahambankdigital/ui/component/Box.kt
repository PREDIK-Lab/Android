package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.PerbandinganPrediksiItem
import com.tugasakhir.prediksisahambankdigital.R
import com.tugasakhir.prediksisahambankdigital.ui.theme.DescriptionBoxText
import com.tugasakhir.prediksisahambankdigital.ui.theme.NumberBoxText
import com.tugasakhir.prediksisahambankdigital.ui.theme.Red1
import com.tugasakhir.prediksisahambankdigital.ui.theme.TitleBoxText
import com.valentinilk.shimmer.shimmer

@Composable
fun WarningBox(
    modifier: Modifier,
    judul: String,
    deskripsi: String
) {
    //val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Red1
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.baseline_warning_24),
                contentDescription = "Peringatan",
                modifier = Modifier
                    .size(95.dp)
                    .padding(start = 15.dp),
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    judul,
                    color = Color.White,
                    modifier = modifier
                        .padding(end = 15.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    letterSpacing = 0.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    deskripsi,
                    color = Color.White,
                    modifier = modifier
                        .padding(end = 15.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    lineHeight = 23.sp,
                    letterSpacing = 0.sp
                )
            }
        }
    }
}

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
            //modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleBoxText(modifier = Modifier, judul = perbandinganPrediksiItem.judul!!)

            perbandinganPrediksiItem.deskripsi?.let {
                DescriptionBoxText(modifier = Modifier, deskripsi = it)
            }

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

                it.deskripsi?.let { deskripsi ->
                    DescriptionBoxText(modifier = Modifier, deskripsi = deskripsi)
                }

                Row(Modifier.weight(1f, false)) {
                    NumberBoxText(modifier = Modifier, judul = it.hargaPenutupan)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(it.gambarKeterangan),
                        contentDescription = "-",
                        modifier = Modifier
                            .size(50.dp)
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