package com.example.myapplication.Page.question2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DepartmentSummary
import com.example.myapplication.data.repository.DepartmentRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface Question2UiState {
    data object Loading : Question2UiState
    data class Success(val summaries: List<DepartmentSummary>) : Question2UiState
    data class Error(val message: String) : Question2UiState
}

class Question2ViewModel(
    private val repository: DepartmentRepository = DepartmentRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<Question2UiState>(Question2UiState.Loading)
    val uiState: StateFlow<Question2UiState> = _uiState.asStateFlow()

    /** null represents the "All Departments" option from the original dropdown. */
    private val _selectedDepartment = MutableStateFlow<String?>(null)
    val selectedDepartment: StateFlow<String?> = _selectedDepartment.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = Question2UiState.Loading
            try {
                val summaries = repository.fetchDepartmentSummaries()
                _uiState.value = Question2UiState.Success(summaries)
            } catch (e: Exception) {
                _uiState.value = Question2UiState.Error(e.message ?: "Failed to load data")
            }
        }
    }

    fun onDepartmentSelected(department: String?) {
        _selectedDepartment.value = department
    }
}
