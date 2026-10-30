package com.example.week6.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week6.viewmodel.CampusSharedViewModel
import com.example.week6.viewmodel.FormViewModel

@Composable
fun ClubProjectsDetailView(
    communityId: String,
    sharedViewModel: CampusSharedViewModel,
    dialogViewModel: FormViewModel
) {
    val club = sharedViewModel.communities.find { it.id == communityId } ?: return
    val listProj = sharedViewModel.projects.filter { it.communityId == communityId }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Project")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(club.fullName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        "${club.memberCount} Anggota | ${listProj.size} Proyek Aktif",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "☐ Daftar Proyek Mahasiswa (${listProj.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (listProj.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada proyek yang dicatat", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listProj) { proj ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(proj.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(proj.description, fontSize = 14.sp, maxLines = 2)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        proj.techStack.forEach { tech ->
                                            AssistChip(
                                                onClick = {},
                                                label = { Text(tech, fontSize = 11.sp) }
                                            )
                                        }
                                    }
                                }
                                IconButton(onClick = { sharedViewModel.deleteProject(proj.id) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddProjectDialog(
            communityName = club.name,
            dialogViewModel = dialogViewModel,
            onDismiss = {
                dialogViewModel.resetForm()
                showDialog = false
            },
            onSave = {
                sharedViewModel.addProject(
                    communityId = communityId,
                    title = dialogViewModel.title,
                    description = dialogViewModel.description,
                    techStack = dialogViewModel.selectedTechStack.value.toList()
                )
                dialogViewModel.resetForm()
                showDialog = false
            }
        )
    }
}
