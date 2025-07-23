package com.edify.learning.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.edify.learning.ui.theme.*

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
    
    object Profile : BottomNavItem(
        route = "profile",
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigateToTab: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Library,
        BottomNavItem.Notes,
        BottomNavItem.Profile
    )
    
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
