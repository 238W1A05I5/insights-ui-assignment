package com.insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.insights.app.ui.theme.*

@Composable
fun AddSymptomDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Long) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var pct by remember { mutableStateOf("10") }
    val palette = listOf(0xFFC8C0E9, 0xFFF5BFC2, 0xFFA6BFA8, 0xFFEFE7D6)
    var color by remember { mutableStateOf(palette.first()) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier.clip(RoundedCornerShape(24.dp)).background(Surface).padding(20.dp)
        ) {
            Text("New symptom", color = TextPrimary, fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))
            LabeledField("Name", name) { name = it }
            Spacer(Modifier.height(8.dp))
            LabeledField("Percent (0-100)", pct) { pct = it.filter { c -> c.isDigit() }.take(3) }
            Spacer(Modifier.height(12.dp))
            Text("Color", color = TextSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Row {
                palette.forEach { c ->
                    Box(
                        Modifier.size(28.dp).padding(end = 8.dp).clip(CircleShape)
                            .background(Color(c))
                            .clickable { color = c }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel", color = TextSecondary, modifier = Modifier.clickable { onDismiss() }.padding(8.dp))
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.clip(RoundedCornerShape(50)).background(Lavender).clickable {
                        if (name.isNotBlank()) {
                            onConfirm(name.trim(), pct.toIntOrNull()?.coerceIn(1, 100) ?: 10, color)
                        }
                    }.padding(horizontal = 16.dp, vertical = 8.dp)
                ) { Text("Add", color = TextPrimary) }
            }
        }
    }
}

@Composable
fun AddCorrelationDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, List<Int>) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    val intensities = remember { mutableStateListOf(1, 1, 1, 1) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier.clip(RoundedCornerShape(24.dp)).background(Surface).padding(20.dp)
        ) {
            Text("New factor", color = TextPrimary, fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))
            LabeledField("Factor name", name) { name = it }
            Spacer(Modifier.height(12.dp))
            Text("Initial intensities (tap to cycle 0–3)", color = TextSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Row {
                intensities.forEachIndexed { i, v ->
                    val a = when (v) { 0 -> 0.10f; 1 -> 0.35f; 2 -> 0.60f; else -> 0.95f }
                    Box(
                        Modifier.weight(1f).padding(horizontal = 3.dp).height(36.dp)
                            .clip(RoundedCornerShape(8.dp)).background(Lavender.copy(alpha = a))
                            .clickable { intensities[i] = (v + 1) % 4 }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel", color = TextSecondary, modifier = Modifier.clickable { onDismiss() }.padding(8.dp))
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.clip(RoundedCornerShape(50)).background(Lavender).clickable {
                        if (name.isNotBlank()) onConfirm(name.trim(), intensities.toList())
                    }.padding(horizontal = 16.dp, vertical = 8.dp)
                ) { Text("Add", color = TextPrimary) }
            }
        }
    }
}

@Composable
private fun LabeledField(label: String, value: String, onChange: (String) -> Unit) {
    Column {
        Text(label, color = TextSecondary, fontSize = 12.sp)
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                .background(Background).padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            BasicTextField(
                value = value, onValueChange = onChange,
                singleLine = true,
                textStyle = TextStyle(color = TextPrimary, fontSize = 14.sp)
            )
        }
    }
}
