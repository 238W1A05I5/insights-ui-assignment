package com.insights.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.insights.app.ui.components.AddSymptomDialog
import com.insights.app.ui.components.BottomNavBar
import com.insights.app.ui.components.NavTab
import com.insights.app.ui.theme.Background
import com.insights.app.viewmodel.InsightsViewModel

@Composable
fun AppRoot() {
    val vm: InsightsViewModel = viewModel()
    var tab by rememberSaveable { mutableStateOf(NavTab.Insights) }
    var showFabDialog by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().background(Background)) {
        when (tab) {
            NavTab.Home -> PlaceholderScreen("Home", "Your daily overview")
            NavTab.Calendar -> PlaceholderScreen("Calendar", "Cycle calendar coming soon")
            NavTab.Insights -> InsightsScreen(vm)
            NavTab.Profile -> PlaceholderScreen("Profile", "Manage your account")
        }
        BottomNavBar(
            selected = tab,
            onSelect = { tab = it },
            onFabClick = { showFabDialog = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
        if (showFabDialog) {
            AddSymptomDialog(
                onDismiss = { showFabDialog = false },
                onConfirm = { n, p, c -> vm.addSymptom(n, p, c); showFabDialog = false }
            )
        }
    }
}
