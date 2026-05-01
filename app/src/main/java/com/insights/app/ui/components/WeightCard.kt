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
import com.insights.app.data.TimeRange
import com.insights.app.data.WeightPoint
import com.insights.app.ui.theme.*

@Composable
fun WeightCard(
    points: List<WeightPoint>,
    range: TimeRange,
    onRangeChange: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    val latest = points.lastOrNull()?.value ?: 0f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Surface)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Weight", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(2.dp))
                Text("kg", color = TextSecondary, fontSize = 12.sp)
            }
            Text("%.1f".format(latest), fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = TextPrimary)
        }

        Spacer(Modifier.height(14.dp))

        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val w = size.width
            val h = size.height
            val pad = 8f
            val values = points.map { it.value }
            val minV = (values.min()) - 0.3f
            val maxV = (values.max()) + 0.3f

            val pts = values.mapIndexed { i, v ->
                val x = pad + (w - pad * 2) * (i.toFloat() / (values.size - 1).coerceAtLeast(1))
                val y = h - pad - (h - pad * 2) * ((v - minV) / (maxV - minV))
                Offset(x, y)
            }

            // soft fill under the curve
            drawPath(
                smoothFilledPath(pts, h - pad),
                brush = Brush.verticalGradient(listOf(Sage.copy(alpha = 0.35f), Sage.copy(alpha = 0.02f)))
            )
            // smooth curved line
            drawPath(
                smoothPath(pts),
                color = Sage,
                style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            // last dot
            drawCircle(Sage, 6f, pts.last())
            drawCircle(Color.White, 3f, pts.last())
        }

        Spacer(Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                points.forEach {
                    Text(it.label, fontSize = 10.sp, color = TextMuted, modifier = Modifier.padding(end = 8.dp))
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        SegmentedToggle(
            options = listOf("Weekly", "Monthly"),
            selectedIndex = if (range == TimeRange.Weekly) 0 else 1,
            onSelect = { onRangeChange(if (it == 0) TimeRange.Weekly else TimeRange.Monthly) },
            activeColor = Sage,
            modifier = Modifier.align(Alignment.End)
        )
    }
}
