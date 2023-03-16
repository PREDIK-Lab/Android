package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugasakhir.prediksisahambankdigital.PerbandinganPrediksiItem
import com.tugasakhir.prediksisahambankdigital.ui.theme.DarkBlue1

@Composable
fun PerbandinganPrediksiBox(
    modifier: Modifier,
    perbandinganPrediksiItem: PerbandinganPrediksiItem
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Text(
                perbandinganPrediksiItem.judul!!,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp),
                color = DarkBlue1,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Row {
                Text(
                    perbandinganPrediksiItem.hargaPenutupan.toString(),
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 15.dp),
                    color = DarkBlue1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp
                )

                Spacer(Modifier.weight(1f))

                Image(
                    painter = painterResource(perbandinganPrediksiItem.gambarKeterangan),
                    contentDescription = "Up",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 15.dp),
                )
            }
        }
    }
}