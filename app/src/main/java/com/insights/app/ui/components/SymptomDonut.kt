package com.insights.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insights.app.data.SymptomSlice
import com.insights.app.ui.theme.*

@Composable
fun SymptomDonut(
    slices: List<SymptomSlice>,
    onAdd: () -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val total = slices.sumOf { it.percent }.coerceAtLeast(1)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Surface)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Symptom trends", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(2.dp))
                Text("Most reported this cycle", color = TextSecondary, fontSize = 12.sp)
            }
            Box(
                Modifier
                    .size(36.dp).clip(CircleShape).background(LavenderSoft)
                    .clickable { onAdd() },
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Add, null, tint = TextPrimary) }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            Modifier.fillMaxWidth().height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.size(170.dp)) {
                val stroke = 28f
                val diameter = size.minDimension - stroke
                val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
                val arcSize = Size(diameter, diameter)
                var startAngle = -90f
                slices.forEach { s ->
                    val sweep = 360f * (s.percent.toFloat() / total)
                    drawArc(
                        color = Color(s.colorHex),
                        startAngle = startAngle + 2f,
                        sweepAngle = (sweep - 4f).coerceAtLeast(0f),
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = stroke, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                    )
                    startAngle += sweep
                }
            }

            val top = slices.maxByOrNull { it.percent }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${top?.percent ?: 0}%", fontSize = 26.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(top?.name ?: "—", fontSize = 12.sp, color = TextSecondary)
            }
        }

        Spacer(Modifier.height(12.dp))
        slices.forEach { s ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(10.dp).clip(CircleShape).background(Color(s.colorHex)))
                Spacer(Modifier.width(10.dp))
                Text(s.name, fontSize = 13.sp, color = TextPrimary, modifier = Modifier.weight(1f))
                Text("${s.percent}%", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                Spacer(Modifier.width(10.dp))
                Box(
                    Modifier.size(28.dp).clip(CircleShape).background(Background)
                        .clickable { onDelete(s.id) },
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Delete, null, tint = TextSecondary, modifier = Modifier.size(14.dp)) }
            }
        }
    }
}
