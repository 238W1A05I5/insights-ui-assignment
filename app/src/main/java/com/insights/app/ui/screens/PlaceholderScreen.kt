package com.insights.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.insights.app.ui.theme.Background
import com.insights.app.ui.theme.TextPrimary
import com.insights.app.ui.theme.TextSecondary

@Composable
fun PlaceholderScreen(title: String, subtitle: String) {
    Box(
        Modifier.fillMaxSize().background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 24.sp, color = TextPrimary)
            Text(subtitle, color = TextSecondary)
        }
    }
}
