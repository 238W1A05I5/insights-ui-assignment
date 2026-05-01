package com.insights.app.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insights.app.data.Correlation
import com.insights.app.data.MockData
import com.insights.app.ui.theme.*

@Composable
fun CorrelationGrid(
    rows: List<Correlation>,
    onCellTap: (id: String, col: Int) -> Unit,
    onAdd: () -> Unit,
    onDelete: (String) -> Unit,
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
                Text("Correlation strengths", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(2.dp))
                Text("Tap a cell to adjust", color = TextSecondary, fontSize = 12.sp)
            }
            Box(
                Modifier.size(36.dp).clip(CircleShape).background(SageSoft)
                    .clickable { onAdd() },
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Add, null, tint = TextPrimary) }
        }

        Spacer(Modifier.height(14.dp))

        // Header row
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(72.dp))
            MockData.symptomColumns.forEach { col ->
                Text(
                    col, fontSize = 11.sp, color = TextMuted,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            Spacer(Modifier.width(28.dp))
        }
        Spacer(Modifier.height(8.dp))

        rows.forEach { row ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    row.factor, fontSize = 13.sp, color = TextPrimary,
                    modifier = Modifier.width(72.dp)
                )
                row.intensities.forEachIndexed { idx, intensity ->
                    val alpha = when (intensity) { 0 -> 0.10f; 1 -> 0.35f; 2 -> 0.60f; else -> 0.95f }
                    Box(
                        Modifier
                            .weight(1f).padding(horizontal = 3.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Lavender.copy(alpha = alpha))
                            .clickable { onCellTap(row.id, idx) }
                    )
                }
                Box(
                    Modifier.size(28.dp).clip(CircleShape).background(Background)
                        .clickable { onDelete(row.id) },
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Delete, null, tint = TextSecondary, modifier = Modifier.size(14.dp)) }
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Low", fontSize = 10.sp, color = TextMuted)
            Spacer(Modifier.width(6.dp))
            listOf(0.10f, 0.35f, 0.60f, 0.95f).forEach { a ->
                Box(Modifier.size(width = 18.dp, height = 8.dp)
                    .clip(RoundedCornerShape(50)).background(Lavender.copy(alpha = a)))
                Spacer(Modifier.width(3.dp))
            }
            Spacer(Modifier.width(3.dp))
            Text("High", fontSize = 10.sp, color = TextMuted)
        }
    }
}
