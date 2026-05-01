package com.insights.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.insights.app.data.MockData
import com.insights.app.ui.components.*
import com.insights.app.ui.theme.*
import com.insights.app.viewmodel.InsightsViewModel

@Composable
fun InsightsScreen(vm: InsightsViewModel = viewModel()) {
    val stabilityRange by vm.stabilityRange.collectAsState()
    val cycleRange by vm.cycleRange.collectAsState()
    val weightRange by vm.weightRange.collectAsState()
    val symptoms by vm.symptoms.collectAsState()
    val correlations by vm.correlations.collectAsState()

    var showAddSymptom by rememberSaveable { mutableStateOf(false) }
    var showAddCorrelation by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Insights", fontSize = 28.sp, color = TextPrimary)
                Text("Your personal trends", color = TextSecondary, fontSize = 13.sp)
            }
        }

        StabilityCard(
            data = MockData.stability(stabilityRange),
            range = stabilityRange,
            onRangeChange = vm::setStabilityRange
        )
        CycleTrendsCard(
            bars = MockData.cycle(cycleRange),
            range = cycleRange,
            onRangeChange = vm::setCycleRange
        )
        WeightCard(
            points = MockData.weight(weightRange),
            range = weightRange,
            onRangeChange = vm::setWeightRange
        )
        SymptomDonut(
            slices = symptoms,
            onAdd = { showAddSymptom = true },
            onDelete = vm::deleteSymptom
        )
        CorrelationGrid(
            rows = correlations,
            onCellTap = vm::cycleIntensity,
            onAdd = { showAddCorrelation = true },
            onDelete = vm::deleteCorrelation
        )
    }

    if (showAddSymptom) {
        AddSymptomDialog(
            onDismiss = { showAddSymptom = false },
            onConfirm = { n, p, c -> vm.addSymptom(n, p, c); showAddSymptom = false }
        )
    }
    if (showAddCorrelation) {
        AddCorrelationDialog(
            onDismiss = { showAddCorrelation = false },
            onConfirm = { n, list -> vm.addCorrelation(n, list); showAddCorrelation = false }
        )
    }
}
