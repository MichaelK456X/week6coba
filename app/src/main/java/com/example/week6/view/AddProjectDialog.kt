package com.example.week6.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.week6.viewmodel.FormViewModel

@Composable
fun AddProjectDialog(
    communityName: String,
    dialogViewModel: FormViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    var isTitleFocused by remember { mutableStateOf(false) }
    var isDescFocused by remember { mutableStateOf(false) }

    val titleScale by animateFloatAsState(if (isTitleFocused) 1.01f else 1f)
    val descScale by animateFloatAsState(if (isDescFocused) 1.01f else 1f)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Add Project", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                item {
                    Text("Komunitas: $communityName", fontSize = 12.sp, color = Color.Gray)
                }
                item {
                    OutlinedTextField(
                        value = dialogViewModel.title,
                        onValueChange = { dialogViewModel.onTitleChange(it) },
                        label = { Text("Judul Proyek *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { isTitleFocused = it.isFocused }
                            .padding(vertical = animateDpAsState(if (isTitleFocused) 2.dp else 0.dp).value),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                item {
                    OutlinedTextField(
                        value = dialogViewModel.description,
                        onValueChange = { dialogViewModel.onDescriptionChange(it) },
                        label = { Text("Deskripsi Proyek (Minimal 3 Baris) *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { isDescFocused = it.isFocused }
                            .padding(vertical = animateDpAsState(if (isDescFocused) 2.dp else 0.dp).value),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                item {
                    Text(
                        "Tech Stack (Pilihan Multi-Selection) *",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val row1Options = listOf("Kotlin", "Jetpack Compose")
                            row1Options.forEach { tech ->
                                val isSelected = dialogViewModel.selectedTechStack.value.contains(tech)
                                val elevation by animateDpAsState(if (isSelected) 4.dp else 0.dp)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { dialogViewModel.toggleTechStack(tech) },
                                    label = { Text(tech, fontSize = 12.sp) },
                                    elevation = FilterChipDefaults.filterChipElevation(elevation = elevation)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val row2Options = listOf("Room", "Firebase", "Ktor")
                            row2Options.forEach { tech ->
                                val isSelected = dialogViewModel.selectedTechStack.value.contains(tech)
                                val elevation by animateDpAsState(if (isSelected) 4.dp else 0.dp)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { dialogViewModel.toggleTechStack(tech) },
                                    label = { Text(tech, fontSize = 12.sp) },
                                    elevation = FilterChipDefaults.filterChipElevation(elevation = elevation)
                                )
                            }
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = onSave,
                            enabled = dialogViewModel.isFormValid()
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}
