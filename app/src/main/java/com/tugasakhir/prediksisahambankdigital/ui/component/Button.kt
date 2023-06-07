package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Button(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(all = Dp(10F)),
        enabled = true,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
    )

    // Add text on the button
    {
        Text(text = text, color = Color.White)
    }
}