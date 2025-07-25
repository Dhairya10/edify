package com.edify.learning.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.edify.learning.ui.theme.*
import com.edify.learning.utils.DeveloperMode

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Library : BottomNavItem(
        route = "library",
        title = "Library",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    
    object Notes : BottomNavItem(
        route = "notes",
        title = "Notes",
        selectedIcon = Icons.Filled.Edit,
        unselectedIcon = Icons.Outlined.Edit
    )
    
    object Quest : BottomNavItem(
        route = "quest",
        title = "Quest",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )
    
    object Developer : BottomNavItem(
        route = "developer",
        title = "Dev",
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Outlined.Build
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigateToTab: (String) -> Unit
) {
    val items = buildList {
        add(BottomNavItem.Library)
        add(BottomNavItem.Notes)
        add(BottomNavItem.Quest)
        if (DeveloperMode.ENABLED) {
            add(BottomNavItem.Developer)
        }
    }
    
    NavigationBar(
        containerColor = White,
        contentColor = TextPrimary
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                selected = isSelected,
                onClick = { onNavigateToTab(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SecondaryBlue,
                    selectedTextColor = SecondaryBlue,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = SecondaryBlue.copy(alpha = 0.1f)
                )
            )
        }
    }
}
