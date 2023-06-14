package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MultiSelectorOption {
    Option,
    Background,
}

@Composable
fun MultiSelector(
    options: Map<String, Int>,
    optionsKey: List<String>,
    selectedOption: Pair<String, Int>,
    onOptionSelect: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colors.onPrimary,
    unselectedcolor: Color = MaterialTheme.colors.onSurface,
    state: MultiSelectorState = rememberMultiSelectorState(
        options = optionsKey,
        selectedOption = selectedOption.first,
        selectedColor = selectedColor,
        unselectedColor = unselectedcolor,
    ),
) {
    require(options.size >= 2) { "This composable requires at least 2 options" }
    require(options.contains(selectedOption.first)) { "Invalid selected option [$selectedOption]" }

    LaunchedEffect(key1 = options, key2 = selectedOption) {
        state.selectOption(this, optionsKey.indexOf(selectedOption.first))
    }

    Layout(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(20.dp)
            )
            .background(MaterialTheme.colors.surface),
        content = {
            val colors = state.textColors

            options.toList().forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .layoutId(MultiSelectorOption.Option)
                        .clickable { onOptionSelect(option) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option.first,
                        style = MaterialTheme.typography.body1,
                        color = if (option == selectedOption) Color.White else MaterialTheme.colors.primary, //colors[index],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(MultiSelectorOption.Background)
                    .clip(
                        shape = RoundedCornerShape(
                            20.dp
//                            topStartPercent = state.startCornerPercent,
//                            bottomStartPercent = state.startCornerPercent,
//                            topEndPercent = state.endCornerPercent,
//                            bottomEndPercent = state.endCornerPercent,
                        )
                    )
                    .background(MaterialTheme.colors.primary),
            )
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight,
        )
        val optionPlaceables = measurables
            .filter { measurable -> measurable.layoutId == MultiSelectorOption.Option }
            .map { measurable -> measurable.measure(optionConstraints) }
        val backgroundPlaceable = measurables
            .first { measurable -> measurable.layoutId == MultiSelectorOption.Background }
            .measure(optionConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            backgroundPlaceable.placeRelative(
                x = (state.selectedIndex * optionWidth).toInt(),
                y = 0,
            )
            optionPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0,
                )
            }
        }
    }
}

