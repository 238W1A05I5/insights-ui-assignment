package com.insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insights.app.data.CycleBar
import com.insights.app.data.TimeRange
import com.insights.app.ui.theme.*

@Composable
fun CycleTrendsCard(
    bars: List<CycleBar>,
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
        Text("Cycle trends", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(2.dp))
        Text("Period & ovulation", color = TextSecondary, fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            bars.forEach { bar ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier.width(14.dp).height(100.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (bar.period > 0f) {
                            Box(
                                Modifier.fillMaxWidth()
                                    .fillMaxHeight(bar.period)
                                    .clip(RoundedCornerShape(50))
                                    .background(Pink)
                            )
                        }
                        if (bar.ovulation > 0f) {
                            Box(
                                Modifier.fillMaxWidth()
                                    .fillMaxHeight(bar.ovulation)
                                    .clip(RoundedCornerShape(50))
                                    .background(Lavender)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(bar.label, fontSize = 11.sp, color = TextMuted)
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LegendChip(Pink, "Period")
                Spacer(Modifier.width(8.dp))
                LegendChip(Lavender, "Ovulation")
            }
            SegmentedToggle(
                options = listOf("Weekly", "Monthly"),
                selectedIndex = if (range == TimeRange.Weekly) 0 else 1,
                onSelect = { onRangeChange(if (it == 0) TimeRange.Weekly else TimeRange.Monthly) },
                activeColor = Pink
            )
        }
    }
}

@Composable
private fun LegendChip(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(RoundedCornerShape(50)).background(color))
        Spacer(Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = TextSecondary)
    }
}
