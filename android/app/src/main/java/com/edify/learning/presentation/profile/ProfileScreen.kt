package com.edify.learning.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.R
import com.edify.learning.data.model.UserProfile
import com.edify.learning.presentation.common.AppConstants
import com.edify.learning.presentation.common.Language
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val userState by viewModel.userProfileState.collectAsState()
    val userProfile = userState.userProfile
    
    // Dialog states
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showClassDialog by remember { mutableStateOf(false) }
    var showNameDialog by remember { mutableStateOf(false) }
    var showClearDataConfirmationDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black,
                    titleContentColor = TextPrimary
                ),
                windowInsets = WindowInsets(0.dp)
            )
            
            // Profile Avatar
            Box(
                modifier = Modifier.padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(DarkSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (userProfile?.name?.firstOrNull()?.uppercase() ?: "A"),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }
            
            // User Name
            Text(
                text = userProfile?.name ?: "Alex",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkSurface
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Name Field
                    ProfileField(
                        label = "Name",
                        value = userProfile?.name ?: "Alex",
                        onEdit = { 
                            viewModel.onEditFieldRequested(ProfileField.NAME)
                            showNameDialog = true
                        }
                    )
                    
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = TextSecondary.copy(alpha = 0.3f)
                    )
                    
                    // Language Field
                    ProfileField(
                        label = "Language",
                        value = userProfile?.languagePreference ?: "English",
                        onEdit = { showLanguageDialog = true }
                    )
                    
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = TextSecondary.copy(alpha = 0.3f)
                    )
                    
                    // Class Field
                    ProfileField(
                        label = "Class",
                        value = "${userProfile?.classLevel ?: 1}",
                        onEdit = { showClassDialog = true }
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { showClearDataConfirmationDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkCard,
                    contentColor = ErrorColor
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = ErrorColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Clear Data",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = ErrorColor
                )
            }
        }
        
        // Language selection dialog
        if (showLanguageDialog) {
            LanguageSelectionDialog(
                currentLanguage = userProfile?.languagePreference ?: "English",
                onLanguageSelected = { language ->
                    viewModel.updateLanguage(language)
                    showLanguageDialog = false
                },
                onDismiss = { showLanguageDialog = false }
            )
        }
        
        // Class selection dialog
        if (showClassDialog) {
            ClassSelectionDialog(
                currentClass = userProfile?.classLevel ?: 1,
                onClassSelected = { classLevel ->
                    viewModel.updateClass(classLevel)
                    showClassDialog = false
                },
                onDismiss = { showClassDialog = false }
            )
        }
        
        // Name edit dialog
        if (showNameDialog) {
            NameEditDialog(
                currentName = userProfile?.name ?: "",
                onNameSubmitted = { name ->
                    viewModel.updateName(name)
                    showNameDialog = false
                },
                onDismiss = { showNameDialog = false }
            )
        }
        
        // Clear Data confirmation dialog
        if (showClearDataConfirmationDialog) {
            ClearDataConfirmationDialog(
                onConfirm = {
                    viewModel.onClearDataClicked()
                    showClearDataConfirmationDialog = false
                },
                onDismiss = { showClearDataConfirmationDialog = false }
            )
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = TextSecondary
            )
            
            Text(
                text = value,
                fontSize = 18.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
        
        IconButton(onClick = onEdit) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit $label",
                tint = SecondaryBlue
            )
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Select Language",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Dynamic language grid using AppConstants
                Column {
                    AppConstants.SUPPORTED_LANGUAGES.chunked(2).forEach { languagePair ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            languagePair.forEachIndexed { index, language ->
                                LanguageOption(
                                    displayName = language.displayName,
                                    isSelected = currentLanguage == language.name,
                                    onClick = { onLanguageSelected(language.name) },
                                    modifier = Modifier.weight(1f)
                                )
                                if (index < languagePair.size - 1) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                            // Add empty space if odd number of languages in the row
                            if (languagePair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageOption(
    displayName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) White else DarkCard
        )
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Black else TextPrimary
            )
        }
    }
}

@Composable
fun ClassSelectionDialog(
    currentClass: Int,
    onClassSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Select Class",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Class grid (3 columns x 4 rows)
                Column {
                    for (row in 0 until 4) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (col in 0 until 3) {
                                val classNum = row * 3 + col + 1
                                ClassOption(
                                    classNumber = classNum,
                                    isSelected = classNum == currentClass,
                                    onClick = { onClassSelected(classNum) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ClassOption(
    classNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) White else DarkCard
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = classNumber.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Black else TextPrimary
            )
        }
    }
}

@Composable
fun ClearDataConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = ErrorColor,
                    modifier = Modifier.size(40.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Clear All Data?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "This will reset all your progress. This action cannot be undone",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        border = BorderStroke(1.dp, TextSecondary.copy(alpha = 0.3f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextPrimary
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorColor,
                            contentColor = White
                        )
                    ) {
                        Text(
                            text = "Clear",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NameEditDialog(
    currentName: String,
    onNameSubmitted: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var isNameValid by remember { mutableStateOf(currentName.isNotBlank()) }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Edit Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = name,
                    onValueChange = { 
                        name = it
                        isNameValid = it.trim().isNotBlank() 
                    },
                    label = { Text("Name") },
                    placeholder = { Text("Enter your name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkCard,
                        unfocusedContainerColor = DarkCard,
                        focusedBorderColor = SecondaryBlue,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f),
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isNameValid
                )
                
                if (!isNameValid) {
                    Text(
                        text = "Name cannot be empty",
                        color = ErrorColor,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                IconButton(
                    onClick = { onNameSubmitted(name.trim()) },
                    enabled = isNameValid,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_24dp_ffffff_fill0_wght400_grad0_opsz24),
                        contentDescription = "Save",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
