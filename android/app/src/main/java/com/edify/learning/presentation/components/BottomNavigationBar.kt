package com.edify.learning.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.edify.learning.R
import com.edify.learning.ui.theme.*
import com.edify.learning.utils.DeveloperMode

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIconResId: Int,
    val unselectedIconResId: Int
) {
    object Library : BottomNavItem(
        route = "library",
        title = "Library",
        selectedIconResId = R.drawable.book_5_24dp_ffffff_fill1_wght400_grad0_opsz24,
        unselectedIconResId = R.drawable.book_5_24dp_ffffff_fill0_wght400_grad0_opsz24
    )
    
    object Notes : BottomNavItem(
        route = "notes",
        title = "Notes",
        selectedIconResId = R.drawable.description_24dp_ffffff_fill1_wght400_grad0_opsz24,
        unselectedIconResId = R.drawable.description_24dp_ffffff_fill0_wght400_grad0_opsz24
    )
    
    object Quest : BottomNavItem(
        route = "quest",
        title = "Quest",
        selectedIconResId = R.drawable.stars_24dp_ffffff_fill1_wght400_grad0_opsz24,
        unselectedIconResId = R.drawable.stars_24dp_ffffff_fill0_wght400_grad0_opsz24
    )
    
    object Developer : BottomNavItem(
        route = "developer",
        title = "Dev",
        selectedIconResId = R.drawable.code_blocks_24dp_ffffff_fill1_wght400_grad0_opsz24,
        unselectedIconResId = R.drawable.code_blocks_24dp_ffffff_fill0_wght400_grad0_opsz24
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
        containerColor = Black,
        contentColor = TextPrimary
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (isSelected) item.selectedIconResId else item.unselectedIconResId),
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
