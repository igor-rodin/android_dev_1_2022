package ru.igor.rodin.m13_databinding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.igor.rodin.m13_databinding.R
import ru.igor.rodin.m13_databinding.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels { SearchViewModel.SearchFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchBtn.setOnClickListener {
                searchViewModel.onSearch(searchEditText.text.toString())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    searchViewModel.searchState.collect { state ->
                        updateUI(state)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(state: SearchState) = binding.apply {
        when (state) {
            SearchState.Idle -> searchResult.text =
                getString(R.string.search_result_text)

            SearchState.Loading -> {
                searchInputLayout.error = null
                searchProgress.visibility = View.VISIBLE
                searchBtn.isEnabled = false
                searchResult.text = getString(R.string.loading_search_hint)
            }

            is SearchState.Success -> {
                searchProgress.visibility = View.GONE
                searchBtn.isEnabled = true
                searchResult.text = state.result ?: getString(
                    R.string.search_empty_result, state.query
                )
            }

            is SearchState.Error -> {
                searchInputLayout.error = state.error
                searchResult.text = getString(R.string.search_result_text)
            }
        }
    }
}