package com.yangpixi.rememberdrinking.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author yangpixi
 * @date 2025/12/29 16:21
 * @description 当前喝水状态圆圈
 */

@Composable
fun ProgressCircle(
    progress: Long,
    goal: Int,
    circleWidth: Dp,
    circleColor: Color,
    arcColor: Color
) {
    val safeDivision = if (goal == 0) {
      0f
    } else {
        progress.toFloat() / goal.toFloat()
    }
    val degree = safeDivision.coerceIn(0f, 1f)
    val textProgress: Int = (safeDivision * 100).toInt()

    val animatedProgress by animateFloatAsState(
        targetValue = degree,
        animationSpec = tween(1000)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            drawCircle(
                color = circleColor,
                center = center,
                style = Stroke(width = circleWidth.toPx())
            )
            drawArc(
                color = arcColor,
                startAngle = -90f,
                sweepAngle = 360 * animatedProgress,
                style = Stroke(
                    width = circleWidth.toPx(),
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = size
            )
        }
        Text(text = "当前进度: ${textProgress}%")
    }
}