package com.example.week6.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week6.viewmodel.CampusSharedViewModel

@Composable
fun CommunitiesDirectoryView(
    sharedViewModel: CampusSharedViewModel,
    onNavigateToClubDetail: (String) -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sharedViewModel.communities) { club ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (club.isJoined) {
                        onNavigateToClubDetail(club.id)
                    } else {
                        Toast.makeText(
                            context,
                            "Harap bergabung ke komunitas terlebih dahulu!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(club.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(club.fullName, fontSize = 12.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { sharedViewModel.toggleJoinCommunity(club.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (club.isJoined) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(if (club.isJoined) "Joined" else "Join")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(club.description, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "🔓 Unlocked - Buka Proyek →",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
