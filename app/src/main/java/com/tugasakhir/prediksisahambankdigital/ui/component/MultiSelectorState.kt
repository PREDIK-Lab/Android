package com.tugasakhir.prediksisahambankdigital.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private const val AnimationDurationMillis = 500

@Stable
interface MultiSelectorState {
    val selectedIndex: Float
    val startCornerPercent: Int
    val endCornerPercent: Int
    val textColors: List<Color>

    fun selectOption(scope: CoroutineScope, index: Int)
}

@Stable
class MultiSelectorStateImpl(
    options: List<String>,
    selectedOption: String,
    private val selectedColor: Color,
    private val unselectedColor: Color,
) : MultiSelectorState {

    override val selectedIndex: Float
        get() = _selectedIndex.value

    // 2
    override val startCornerPercent: Int
        get() = _startCornerPercent.value.toInt()

    override val endCornerPercent: Int
        get() = _endCornerPercent.value.toInt()

    override val textColors: List<Color>
        get() = _textColors.value

    private var _selectedIndex = Animatable(options.indexOf(selectedOption).toFloat())

    // 3
    private var _startCornerPercent = Animatable(
        if (options.first() == selectedOption) {
            20f
        } else {
            20f
        }
    )

    // 4
    private var _endCornerPercent = Animatable(
        if (options.last() == selectedOption) {
            20f
        } else {
            20f
        }
    )

    private var _textColors: State<List<Color>> = derivedStateOf {
        List(numOptions) { index ->
            // 5
            lerp(
                start = unselectedColor,
                stop = selectedColor,
                // 6
                fraction = 1f - (((selectedIndex - index.toFloat()).absoluteValue).coerceAtMost(1f))
            )
        }
    }

    private val numOptions = options.size
    private val animationSpec = tween<Float>(
        //durationMillis = AnimationDurationMillis,
        easing = FastOutSlowInEasing,
    )

    override fun selectOption(scope: CoroutineScope, index: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = index.toFloat(),
                animationSpec = animationSpec,
            )
        }

        // 5
        scope.launch {
            _startCornerPercent.animateTo(
                targetValue = if (index == 0) 20f else 20f,
                animationSpec = animationSpec,
            )
        }

        // 6
        scope.launch {
            _endCornerPercent.animateTo(
                targetValue = if (index == numOptions - 1) 20f else 20f,
                animationSpec = animationSpec,
            )
        }
    }
}

@Composable
fun rememberMultiSelectorState(
    options: List<String>,
    selectedOption: String,
    selectedColor: Color,
    unselectedColor: Color,
) = remember {
    MultiSelectorStateImpl(
        options,
        selectedOption,
        selectedColor,
        unselectedColor,
    )
}