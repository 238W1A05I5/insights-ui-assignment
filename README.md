# Insights App — Jetpack Compose

A Material 3 Android app that mirrors the design sample exactly:
- **Smooth (curved) charts** built with cardinal-spline cubic Béziers
- **Backend-style state** via `InsightsViewModel` with `StateFlow`
- **Weekly / Monthly toggles** that swap real datasets across all charts
- **Full CRUD** for symptoms (donut) and correlation factors (heatmap)
- Tap any heatmap cell to cycle its intensity (0 → 1 → 2 → 3 → 0)

## Run
1. Open this folder in **Android Studio Hedgehog or newer**.
2. Let Gradle sync (it will download the wrapper).
3. Run on an API 34 emulator (Pixel 7 recommended).

## Stack
- Kotlin 2.0.0 · AGP 8.5.2 · Gradle 8.7
- Jetpack Compose BOM 2024.09.00 · Material 3
- ViewModel + StateFlow

## Structure
```
app/src/main/java/com/insights/app/
├── MainActivity.kt
├── data/InsightsData.kt          # models + mock weekly/monthly datasets
├── viewmodel/InsightsViewModel.kt # ranges, symptoms CRUD, correlations CRUD
└── ui/
    ├── theme/                    # palette + typography
    ├── components/
    │   ├── SmoothPath.kt         # cardinal-spline curve builder
    │   ├── StabilityCard.kt      # smooth area + dashed trend line
    │   ├── CycleTrendsCard.kt    # capsule bars (period / ovulation)
    │   ├── WeightCard.kt         # smooth line + segmented toggle
    │   ├── SymptomDonut.kt       # donut + add/delete rows
    │   ├── CorrelationGrid.kt    # tappable heatmap + add/delete
    │   ├── SegmentedToggle.kt
    │   ├── Dialogs.kt            # add-symptom / add-correlation
    │   └── BottomNavBar.kt
    └── screens/                  # InsightsScreen, AppRoot, Placeholder
```
