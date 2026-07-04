package com.example.myapplication.Page.question1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val AUTO_REVERT_DELAY_MS = 5000L
class Question1ViewModel : ViewModel() {

    private val _columnByItemId = MutableStateFlow(
        Constants.sortItems.associate { it.id to Constants.UNSORTED },
    )
    val columnByItemId: StateFlow<Map<String, String>> = _columnByItemId.asStateFlow()

    private val revertJobs = mutableMapOf<String, Job>()

    fun onItemClick(itemId: String, itemType: String) {
        val current = _columnByItemId.value[itemId] ?: Constants.UNSORTED
        if (current == Constants.UNSORTED) {
            setColumn(itemId, itemType)
            startRevertTimer(itemId)
        } else {
            cancelRevertTimer(itemId)
            setColumn(itemId, Constants.UNSORTED)
        }
    }

    private fun setColumn(itemId: String, column: String) {
        _columnByItemId.update { it + (itemId to column) }
    }

    private fun startRevertTimer(itemId: String) {
        cancelRevertTimer(itemId)
        revertJobs[itemId] = viewModelScope.launch {
            delay(AUTO_REVERT_DELAY_MS)
            setColumn(itemId, Constants.UNSORTED)
            revertJobs.remove(itemId)
        }
    }

    private fun cancelRevertTimer(itemId: String) {
        revertJobs[itemId]?.cancel()
        revertJobs.remove(itemId)
    }

    override fun onCleared() {
        super.onCleared()
        revertJobs.values.forEach { it.cancel() }
        revertJobs.clear()
    }
}
