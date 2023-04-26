package com.jaikeerthick.composable_graphs.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class GraphData(val text: kotlin.String) {

    @Parcelize
    data class Number(val item: kotlin.Number): GraphData(item.toString()), Parcelable

    @Parcelize
    data class String(val item: kotlin.String): GraphData(item), Parcelable

}