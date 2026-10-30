package com.example.week6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week6.model.Community
import com.example.week6.model.Project

class ClubProjectsDetailViewModel(private val sharedViewModel: CampusSharedViewModel) : ViewModel() {
    fun getCommunity(id: String): Community? {
        return sharedViewModel.communities.find { it.id == id }
    }

    fun getProjectsForCommunity(communityId: String): List<Project> {
        return sharedViewModel.projects.filter { it.communityId == communityId }
    }

    fun deleteProject(projectId: String) {
        sharedViewModel.deleteProject(projectId)
    }
}
