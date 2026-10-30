package com.example.week6.model

data class Community(
    val id: String,
    val name: String,
    val fullName: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean = false
)
