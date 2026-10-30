package com.example.week6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week6.model.Community
import com.example.week6.model.StudentProfile
import com.example.week6.model.Project

class DashboardViewModel(private val sharedViewModel: CampusSharedViewModel) : ViewModel() {
    val profile: StudentProfile get() = sharedViewModel.profileState.value
    val joinedClubsCount: Int get() = sharedViewModel.communities.count { it.isJoined }
    val totalProjectsCount: Int get() = sharedViewModel.projects.size
    val allProjects: List<Project> get() = sharedViewModel.projects
    val allCommunities: List<Community> get() = sharedViewModel.communities

    fun getCommunityName(id: String): String {
        return sharedViewModel.communities.find { it.id == id }?.name ?: ""
    }
}
