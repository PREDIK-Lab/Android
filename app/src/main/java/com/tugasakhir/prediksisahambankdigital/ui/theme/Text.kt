package com.tugasakhir.prediksisahambankdigital.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(modifier: Modifier, judul: String) {
    Text(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        text = judul,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.sp
    )
}

@Composable
fun SubTitleText(modifier: Modifier, judul: String) {
    Text(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        text = judul,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    )
}

@Composable
fun TitleBoxText(modifier: Modifier, judul: String) {
    Text(
        judul,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp),
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.sp
    )
}

@Composable
fun DescriptionBoxText(modifier: Modifier, deskripsi: String) {
    Text(
        deskripsi,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp),
        color = DarkGrey1,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    )
}

@Composable
fun NumberBoxText(modifier: Modifier, judul: Number, fontSize: TextUnit = 23.sp) {
    Text(
        judul.toString(),
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 15.dp),
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        letterSpacing = 0.sp
    )
}

@Composable
fun ListTitleText(modifier: Modifier, judul: String) {
    Text(
        judul,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    )
}

@Composable
fun ListItemText(modifier: Modifier, judul: String) {
    Text(
        judul,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
        fontSize = 15.sp,
        letterSpacing = 0.sp
    )
}

@Composable
fun ListItemClickableText(modifier: Modifier, judul: String) {
    Text(
        judul,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
        color = MaterialTheme.colors.primary,
        fontSize = 15.sp,
        letterSpacing = 0.sp
    )
}

@Composable
fun DescriptionText(modifier: Modifier, deskripsi: String) {
    Text(
        text = deskripsi,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp),
        fontSize = 15.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
}

@Composable
fun ButtonText(modifier: Modifier, onClick: () -> Unit, judul: String) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Text(text = judul, color = Color.White, fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
    }
}

@Composable
fun ClickableTextStyle() = TextStyle(
    fontSize = 15.sp,
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colors.primary,
    letterSpacing = 0.sp
)