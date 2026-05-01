package com.insights.app.viewmodel

import androidx.lifecycle.ViewModel
import com.insights.app.data.Correlation
import com.insights.app.data.MockData
import com.insights.app.data.SymptomSlice
import com.insights.app.data.TimeRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class InsightsViewModel : ViewModel() {

    private val _stabilityRange = MutableStateFlow(TimeRange.Weekly)
    val stabilityRange = _stabilityRange.asStateFlow()

    private val _cycleRange = MutableStateFlow(TimeRange.Weekly)
    val cycleRange = _cycleRange.asStateFlow()

    private val _weightRange = MutableStateFlow(TimeRange.Monthly)
    val weightRange = _weightRange.asStateFlow()

    private val _symptoms = MutableStateFlow(MockData.initialSymptoms)
    val symptoms = _symptoms.asStateFlow()

    private val _correlations = MutableStateFlow(MockData.initialCorrelations)
    val correlations = _correlations.asStateFlow()

    fun setStabilityRange(r: TimeRange) { _stabilityRange.value = r }
    fun setCycleRange(r: TimeRange) { _cycleRange.value = r }
    fun setWeightRange(r: TimeRange) { _weightRange.value = r }

    // ---- Symptoms CRUD ----
    fun addSymptom(name: String, percent: Int, colorHex: Long) {
        _symptoms.value = _symptoms.value + SymptomSlice(UUID.randomUUID().toString(), name, percent, colorHex)
    }
    fun updateSymptom(id: String, name: String, percent: Int) {
        _symptoms.value = _symptoms.value.map {
            if (it.id == id) it.copy(name = name, percent = percent) else it
        }
    }
    fun deleteSymptom(id: String) {
        _symptoms.value = _symptoms.value.filterNot { it.id == id }
    }

    // ---- Correlations CRUD ----
    fun addCorrelation(factor: String, intensities: List<Int>) {
        _correlations.value = _correlations.value + Correlation(UUID.randomUUID().toString(), factor, intensities)
    }
    fun updateCorrelation(id: String, factor: String, intensities: List<Int>) {
        _correlations.value = _correlations.value.map {
            if (it.id == id) it.copy(factor = factor, intensities = intensities) else it
        }
    }
    fun deleteCorrelation(id: String) {
        _correlations.value = _correlations.value.filterNot { it.id == id }
    }
    fun cycleIntensity(id: String, columnIdx: Int) {
        _correlations.value = _correlations.value.map { c ->
            if (c.id == id) {
                val newList = c.intensities.toMutableList()
                if (columnIdx in newList.indices) newList[columnIdx] = (newList[columnIdx] + 1) % 4
                c.copy(intensities = newList)
            } else c
        }
    }
}
