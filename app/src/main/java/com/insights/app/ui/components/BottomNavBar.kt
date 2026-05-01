package com.insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.insights.app.ui.theme.Lavender
import com.insights.app.ui.theme.Surface
import com.insights.app.ui.theme.TextMuted
import com.insights.app.ui.theme.TextPrimary

enum class NavTab { Home, Calendar, Insights, Profile }

@Composable
fun BottomNavBar(
    selected: NavTab,
    onSelect: (NavTab) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Surface)
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavItem(Icons.Default.Home, NavTab.Home, selected, onSelect)
            NavItem(Icons.Default.CalendarMonth, NavTab.Calendar, selected, onSelect)
            Spacer(Modifier.size(48.dp)) // FAB hole
            NavItem(Icons.Default.Insights, NavTab.Insights, selected, onSelect)
            NavItem(Icons.Default.Person, NavTab.Profile, selected, onSelect)
        }
        // FAB
        Box(
            Modifier
                .size(56.dp).clip(CircleShape).background(Lavender)
                .clickable { onFabClick() },
            contentAlignment = Alignment.Center
        ) { Icon(Icons.Default.Add, null, tint = TextPrimary) }
    }
}

@Composable
private fun NavItem(icon: ImageVector, tab: NavTab, selected: NavTab, onSelect: (NavTab) -> Unit) {
    val active = tab == selected
    Box(
        Modifier.size(40.dp).clip(CircleShape)
            .background(if (active) Lavender.copy(alpha = 0.35f) else Color.Transparent)
            .clickable { onSelect(tab) },
        contentAlignment = Alignment.Center
    ) { Icon(icon, null, tint = if (active) TextPrimary else TextMuted) }
}
