package com.example.week6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.week6.model.Community

class DirectoryViewModel(private val sharedViewModel: CampusSharedViewModel) : ViewModel() {
    val communitiesList: List<Community> get() = sharedViewModel.communities

    fun toggleJoin(communityId: String) {
        sharedViewModel.toggleJoinCommunity(communityId)
    }
}
