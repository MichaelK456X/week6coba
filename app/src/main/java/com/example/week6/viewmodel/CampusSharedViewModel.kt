package com.example.week6.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.week6.model.Community
import com.example.week6.model.Data
import com.example.week6.model.Project
import java.util.UUID

class CampusSharedViewModel : ViewModel() {
    val profileState = mutableStateOf(Data.profile)
    val communities = mutableStateListOf<Community>().apply { addAll(Data.initialCommunities) }
    val projects = mutableStateListOf<Project>()

    fun toggleJoinCommunity(communityId: String) {
        val index = communities.indexOfFirst { it.id == communityId }
        if (index != -1) {
            val current = communities[index]
            communities[index] = current.copy(isJoined = !current.isJoined)
        }
    }

    fun addProject(communityId: String, title: String, description: String, techStack: List<String>) {
        val newProject = Project(
            id = UUID.randomUUID().toString(),
            communityId = communityId,
            title = title,
            description = description,
            techStack = techStack
        )
        projects.add(newProject)
    }

    fun deleteProject(projectId: String) {
        projects.removeAll { it.id == projectId }
    }
}
