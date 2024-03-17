package ru.igor.rodin.m13_databinding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.igor.rodin.m13_databinding.R
import ru.igor.rodin.m13_databinding.SearchApp
import ru.igor.rodin.m13_databinding.SearchEngine
import ru.igor.rodin.m13_databinding.common.StringResourceHelper

private const val QUERY_MIN_LENGTH = 3
private const val SEARCH_DELAY = 500L


data class SearchInput(var query: String = "")
sealed class SearchState(open val error: String? = null, open val infoStatus: String? = null) {
    data class Idle(override val infoStatus: String?) : SearchState(infoStatus = infoStatus)
    data class Loading(override val infoStatus: String?) : SearchState(infoStatus = infoStatus)
    data class Canceled(val query: String, override val infoStatus: String?) :
        SearchState(infoStatus = infoStatus)

    data class Success(val query: String, val result: String?, override val infoStatus: String?) :
        SearchState(infoStatus = infoStatus)

    data class Error(override val error: String?, override val infoStatus: String?) :
        SearchState(error = error, infoStatus = infoStatus)
}

class SearchViewModel(
    private val searchEngine: SearchEngine,
    private val stringResourceHelper: StringResourceHelper
) : ViewModel() {

    private var _searchInput = MutableStateFlow(SearchInput())
    val searchInput get() = _searchInput.asStateFlow()

    private val _searchState: MutableStateFlow<SearchState> =
        MutableStateFlow(SearchState.Idle(stringResourceHelper.getString(R.string.search_result_text)))
    val searchState get() = _searchState.asStateFlow()

    private var searchJob: Job? = null

    private var inputJob: Job? = null
    @OptIn(FlowPreview::class)
    fun onTextChanged() {
        inputJob?.cancel()
        inputJob = searchInput
            .debounce(SEARCH_DELAY)
            .onEach {
                onSearch(it.query)
            }
            .launchIn(viewModelScope)
    }

    private fun onSearch(query: String) {
        if (searchJob?.isCompleted == false) {
            cancelSearch(query)
        }
        doSearch(query)
    }

    private fun doSearch(query: String) {
        searchJob = viewModelScope.launch {
            if (isQueryValid(query)) {
                _searchState.value =
                    SearchState.Loading(stringResourceHelper.getString(R.string.loading_search_hint))
                val result = searchEngine.search(query)
                val infoStatus =
                    result ?: stringResourceHelper.getString(R.string.search_empty_result, query)
                _searchState.value = SearchState.Success(query, result, infoStatus)
            } else {
                _searchState.value =
                    SearchState.Error(
                        stringResourceHelper.getString(
                            R.string.query_to_short_error_message,
                            QUERY_MIN_LENGTH
                        ), stringResourceHelper.getString(
                            R.string.search_result_text
                        )
                    )
            }

        }
    }

    private fun cancelSearch(query: String) {
        _searchState.value = SearchState.Canceled(
            query,
            stringResourceHelper.getString(R.string.canceled_search_hint, query)
        )
        searchJob?.cancel()
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