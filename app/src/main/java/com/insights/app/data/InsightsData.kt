package com.insights.app.data

enum class TimeRange { Weekly, Monthly }

data class StabilityData(val score: Int, val series: List<Float>, val highlight: List<Float>)

data class CycleBar(val label: String, val period: Float, val ovulation: Float)

data class WeightPoint(val label: String, val value: Float)

data class SymptomSlice(
    val id: String,
    val name: String,
    val percent: Int,
    val colorHex: Long
)

data class Correlation(
    val id: String,
    val factor: String,
    val intensities: List<Int> // 0..3 per symptom column
)

object MockData {
    val symptomColumns = listOf("Mood", "Pain", "Energy", "Sleep")

    fun stability(range: TimeRange): StabilityData = when (range) {
        // Weekly: day-to-day variance, ends at the score
        TimeRange.Weekly -> StabilityData(
            score = 72,
            series    = listOf(58f, 62f, 55f, 68f, 64f, 74f, 72f),
            highlight = listOf(50f, 55f, 58f, 62f, 66f, 70f, 72f)
        )
        // Monthly: smoother upward trend across 12 weeks
        TimeRange.Monthly -> StabilityData(
            score = 78,
            series    = listOf(55f, 60f, 58f, 64f, 62f, 68f, 70f, 73f, 71f, 75f, 78f, 78f),
            highlight = listOf(48f, 53f, 57f, 60f, 63f, 66f, 69f, 72f, 74f, 76f, 77f, 78f)
        )
    }

    fun cycle(range: TimeRange): List<CycleBar> = when (range) {
        // Weekly: real day-of-week pattern (period tail → ovulation peak → calm)
        TimeRange.Weekly -> listOf(
            CycleBar("M", 0.55f, 0f),
            CycleBar("T", 0.35f, 0f),
            CycleBar("W", 0f,    0.30f),
            CycleBar("T", 0f,    0.65f),
            CycleBar("F", 0f,    0.85f),
            CycleBar("S", 0f,    0.45f),
            CycleBar("S", 0f,    0.20f),
        )
        // Monthly: 4-week cycle arc
        TimeRange.Monthly -> listOf(
            CycleBar("W1", 0.80f, 0f),
            CycleBar("W2", 0.30f, 0f),
            CycleBar("W3", 0f,    0.85f),
            CycleBar("W4", 0f,    0.35f),
        )
    }

    fun weight(range: TimeRange): List<WeightPoint> = when (range) {
        // Weekly: small daily fluctuations around 62.x
        TimeRange.Weekly -> listOf(
            WeightPoint("M", 62.1f), WeightPoint("T", 62.4f), WeightPoint("W", 62.0f),
            WeightPoint("T", 62.6f), WeightPoint("F", 62.3f), WeightPoint("S", 62.8f),
            WeightPoint("S", 62.5f),
        )
        // Monthly: clear downward weight-loss arc with a mid-bump
        TimeRange.Monthly -> listOf(
            WeightPoint("Jan", 63.5f), WeightPoint("Feb", 63.2f), WeightPoint("Mar", 62.9f),
            WeightPoint("Apr", 63.0f), WeightPoint("May", 62.6f), WeightPoint("Jun", 62.4f),
            WeightPoint("Jul", 62.5f),
        )
    }

    val initialSymptoms = listOf(
        SymptomSlice("s1", "Cramps", 35, 0xFFC8C0E9),
        SymptomSlice("s2", "Headache", 25, 0xFFF5BFC2),
        SymptomSlice("s3", "Fatigue", 22, 0xFFA6BFA8),
        SymptomSlice("s4", "Bloating", 18, 0xFFEFE7D6),
    )

    val initialCorrelations = listOf(
        Correlation("c1", "Sleep",     listOf(3, 1, 3, 3)),
        Correlation("c2", "Exercise",  listOf(2, 2, 3, 2)),
        Correlation("c3", "Diet",      listOf(2, 3, 2, 1)),
        Correlation("c4", "Stress",    listOf(3, 3, 1, 2)),
        Correlation("c5", "Hydration", listOf(1, 2, 2, 2)),
    )
}
