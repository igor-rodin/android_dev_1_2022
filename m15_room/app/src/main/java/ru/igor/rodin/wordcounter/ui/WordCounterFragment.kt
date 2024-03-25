package ru.igor.rodin.wordcounter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import ru.igor.rodin.wordcounter.databinding.FragmentWordCounterBinding

class WordCounterFragment : Fragment() {
    private var _binding: FragmentWordCounterBinding? = null
    private val binding get() = _binding!!

    private val wordsViewModel: WordsViewModel by viewModels { WordsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            wordEdit.doAfterTextChanged { text ->  validate(text) }

            btnAdd.setOnClickListener {
                val word = wordEdit.text.toString()
                wordsViewModel.insertOrUpdate(word)
            }

            btnClear.setOnClickListener {
                wordsViewModel.clear()
            }
        }
    }

    private fun isValid(text: CharSequence?): Boolean {
        return text?.isNotEmpty() == true
    }

    private fun validate(text: CharSequence?) {
        if (isValid(text)){
            binding.wordEdit.error = null
            binding.btnAdd.isEnabled = true
        } else {
            binding.wordEdit.error = "Invalid word"
            binding.btnAdd.isEnabled = false
        }
    }

}