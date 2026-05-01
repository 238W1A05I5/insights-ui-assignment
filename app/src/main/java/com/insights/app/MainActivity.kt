package com.insights.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.insights.app.ui.screens.AppRoot
import com.insights.app.ui.theme.Background
import com.insights.app.ui.theme.InsightsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            InsightsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(Background),
                    color = Background
                ) {
                    Box(
                        Modifier.fillMaxSize()
                            .windowInsetsPadding(WindowInsets.systemBars)
                    ) { AppRoot() }
                }
            }
        }
    }
}
