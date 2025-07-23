package com.edify.learning.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Avatar
        Card(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = SecondaryBlue)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(60.dp),
                    tint = White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // User Name
        Text(
            text = "Alex",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        
        Text(
            text = "Student",
            fontSize = 16.sp,
            color = TextSecondary
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Profile Options
        ProfileOption(
            icon = Icons.Default.Settings,
            title = "Settings",
            onClick = { /* TODO: Navigate to settings */ }
        )
        
        ProfileOption(
            icon = Icons.Default.Info,
            title = "About",
            onClick = { /* TODO: Navigate to about */ }
        )
        
        ProfileOption(
            icon = Icons.Default.ExitToApp,
            title = "Sign Out",
            onClick = { /* TODO: Handle sign out */ }
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // App Version
        Text(
            text = "Version 1.0.0",
            fontSize = 14.sp,
            color = TextSecondary
        )
    }
}

@Composable
private fun ProfileOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = SecondaryBlue,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = title,
                fontSize = 16.sp,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
