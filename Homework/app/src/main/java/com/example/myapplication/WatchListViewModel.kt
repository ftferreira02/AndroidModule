package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class WatchItem(val id: Int, val title: String, val watched: Boolean = false)

class WatchListViewModel : ViewModel() {
    private val _watchList = MutableStateFlow(
        listOf(
            WatchItem(1, "Breaking Bad"),
            WatchItem(2, "Stranger Things"),
            WatchItem(3, "Inception"),
            WatchItem(4, "The Dark Knight")
        )
    )
    val watchList = _watchList.asStateFlow()

    fun addItem(title: String) {
        val newId = (_watchList.value.maxOfOrNull { it.id } ?: 0) + 1
        _watchList.value = _watchList.value + WatchItem(newId, title)
    }

    fun toggleWatched(itemId: Int) {
        _watchList.value = _watchList.value.map {
            if (it.id == itemId) it.copy(watched = !it.watched) else it
        }
    }

    fun removeItem(itemId: Int) {
        _watchList.value = _watchList.value.filterNot { it.id == itemId }
    }
}