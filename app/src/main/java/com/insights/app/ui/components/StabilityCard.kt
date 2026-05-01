package com.insights.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insights.app.data.StabilityData
import com.insights.app.data.TimeRange
import com.insights.app.ui.theme.*

@Composable
fun StabilityCard(
    data: StabilityData,
    range: TimeRange,
    onRangeChange: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Surface)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Stability summary", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(2.dp))
                Text("Overall wellbeing index", color = TextSecondary, fontSize = 12.sp)
            }
            Box(
                Modifier.clip(RoundedCornerShape(50))
                    .background(LavenderSoft)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text("${data.score}", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextPrimary)
            }
        }
        Spacer(Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            val w = size.width
            val h = size.height
            val pad = 6f
            val maxV = (data.series + data.highlight).max()
            val minV = 0f

            fun toPoints(values: List<Float>): List<Offset> = values.mapIndexed { i, v ->
                val x = pad + (w - pad * 2) * (i.toFloat() / (values.size - 1).coerceAtLeast(1))
                val y = h - pad - (h - pad * 2) * ((v - minV) / (maxV - minV).coerceAtLeast(1f))
                Offset(x, y)
            }

            // dashed grid lines
            val dash = PathEffect.dashPathEffect(floatArrayOf(6f, 8f))
            for (i in 1..3) {
                val y = h * (i / 4f)
                drawLine(GridLine, Offset(0f, y), Offset(w, y), strokeWidth = 1f, pathEffect = dash)
            }

            // filled area (lavender soft)
            val areaPts = toPoints(data.series)
            drawPath(
                smoothFilledPath(areaPts, h - pad),
                brush = Brush.verticalGradient(listOf(Lavender.copy(alpha = 0.55f), Lavender.copy(alpha = 0.05f)))
            )
            // smooth main line
            drawPath(smoothPath(areaPts), color = Lavender, style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round))

            // dashed highlight smooth line
            val hlPts = toPoints(data.highlight)
            drawPath(
                smoothPath(hlPts),
                color = Sage,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round, pathEffect = dash)
            )
            // end dot
            drawCircle(Sage, radius = 5f, center = hlPts.last())
            drawCircle(Color.White, radius = 2.5f, center = hlPts.last())
        }

        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LegendDot(Lavender); Spacer(Modifier.width(4.dp))
                Text("Mood", fontSize = 11.sp, color = TextSecondary)
                Spacer(Modifier.width(10.dp))
                LegendDot(Sage); Spacer(Modifier.width(4.dp))
                Text("Trend", fontSize = 11.sp, color = TextSecondary)
            }
            SegmentedToggle(
                options = listOf("Weekly", "Monthly"),
                selectedIndex = if (range == TimeRange.Weekly) 0 else 1,
                onSelect = { onRangeChange(if (it == 0) TimeRange.Weekly else TimeRange.Monthly) },
                activeColor = Lavender
            )
        }
    }
}

@Composable
private fun LegendDot(c: Color) {
    Box(Modifier.size(8.dp).clip(RoundedCornerShape(50)).background(c))
}
