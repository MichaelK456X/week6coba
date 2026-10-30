package com.example.week6.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var selectedTechStack = mutableStateOf(setOf<String>())
        private set

    fun onTitleChange(value: String) {
        title = value
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun toggleTechStack(tech: String) {
        val current = selectedTechStack.value
        selectedTechStack.value = if (current.contains(tech)) current - tech else current + tech
    }

    fun isFormValid(): Boolean {
        return title.isNotBlank() && description.isNotBlank() && selectedTechStack.value.isNotEmpty()
    }

    fun resetForm() {
        title = ""
        description = ""
        selectedTechStack.value = emptySet()
    }
}
