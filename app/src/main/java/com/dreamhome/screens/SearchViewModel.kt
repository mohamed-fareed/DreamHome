package com.dreamhome.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreamhome.data.entities.SearchResult
import com.dreamhome.data.search.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        fetchSearchResults()
    }

    private fun fetchSearchResults() = viewModelScope.launch {
        _state.value = State.Loading
        try {
            val result = searchRepository.getSearchResults()
            _state.value = State.Success(result)
        } catch (e: Exception) {
            _state.value = State.Error(e.message ?: "An error occurred")
        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val result: SearchResult) : State()
        data class Error(val message: String) : State()
    }
}