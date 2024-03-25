package ru.igor.rodin.wordcounter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.igor.rodin.wordcounter.R
import ru.igor.rodin.wordcounter.databinding.FragmentWordCounterBinding

class WordCounterFragment : Fragment() {
    private var _binding: FragmentWordCounterBinding? = null
    private val binding get() = _binding!!

    private val wordsViewModel: WordsViewModel by viewModels { WordsViewModel.Factory }
    private val lettersOrHyphensRegex =
        """^[а-яёa-z]+([-]?[а-яёa-z)])*$""".toRegex(RegexOption.IGNORE_CASE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordCounterBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            wordEdit.doAfterTextChanged { text -> updateUI(isValid(text)) }

            btnAdd.setOnClickListener {
                val word = wordEdit.text.toString().lowercase()
                wordsViewModel.insertOrUpdate(word)
            }

            btnClear.setOnClickListener {
                wordsViewModel.clear()
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    wordsViewModel.firstMostCommon.collect { words ->
                        dbWords.text = words.joinToString("\n")
                    }
                }
            }
        }

    }

    private fun isValid(text: CharSequence?): Boolean {
        return text?.matches(lettersOrHyphensRegex) == true
    }

    private fun updateUI(isInputValid: Boolean) {
        if (isInputValid) {
            binding.wordEdit.error = null
            binding.btnAdd.isEnabled = true
        } else {
            binding.wordEdit.error = getString(R.string.input_error_hint)
            binding.btnAdd.isEnabled = false
        }
    }
}