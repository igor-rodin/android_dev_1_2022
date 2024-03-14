package ru.igor.rodin.m13_databinding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.igor.rodin.m13_databinding.R
import ru.igor.rodin.m13_databinding.SearchApp
import ru.igor.rodin.m13_databinding.SearchEngine
import ru.igor.rodin.m13_databinding.StringResourceHelper

private const val QUERY_MIN_LENGTH = 3

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val query: String, val result: String?) : SearchState()
    data class Error(val error: String) : SearchState()
}

class SearchViewModel(
    private val searchEngine: SearchEngine,
    private val stringResourceHelper: StringResourceHelper
) : ViewModel() {
    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Idle)
    val searchState get() = _searchState.asStateFlow()
    fun onSearch(query: String) = viewModelScope.launch {
        if (isQueryValid(query)) {
            _searchState.value = SearchState.Loading
            val result = searchEngine.search(query)
            _searchState.value = SearchState.Success(query, result)

        } else {
            _searchState.value =
                SearchState.Error(
                    stringResourceHelper.getString(
                        R.string.query_to_short_error_message,
                        QUERY_MIN_LENGTH
                    )
                )
        }

    }

    private fun isQueryValid(query: String) = query.isNotBlank() && query.length >= QUERY_MIN_LENGTH

    companion object {
        val SearchFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                    return SearchViewModel(
                        SearchEngine(),
                        StringResourceHelper(SearchApp.getContext())
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}