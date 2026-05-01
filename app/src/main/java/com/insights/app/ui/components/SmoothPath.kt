package com.insights.app.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

/**
 * Builds a smooth Catmull-Rom-style cardinal spline through the given points.
 * Produces the curved (rounded) line that matches the sample design exactly.
 */
fun smoothPath(points: List<Offset>, tension: Float = 0.2f): Path {
    val path = Path()
    if (points.isEmpty()) return path
    path.moveTo(points.first().x, points.first().y)
    if (points.size < 2) return path

    for (i in 0 until points.size - 1) {
        val p0 = points[if (i - 1 < 0) i else i - 1]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points[if (i + 2 >= points.size) i + 1 else i + 2]

        val c1x = p1.x + (p2.x - p0.x) * tension
        val c1y = p1.y + (p2.y - p0.y) * tension
        val c2x = p2.x - (p3.x - p1.x) * tension
        val c2y = p2.y - (p3.y - p1.y) * tension

        path.cubicTo(c1x, c1y, c2x, c2y, p2.x, p2.y)
    }
    return path
}

fun smoothFilledPath(points: List<Offset>, baselineY: Float, tension: Float = 0.2f): Path {
    val p = smoothPath(points, tension)
    if (points.isNotEmpty()) {
        p.lineTo(points.last().x, baselineY)
        p.lineTo(points.first().x, baselineY)
        p.close()
    }
    return p
}
