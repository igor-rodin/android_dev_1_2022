package ru.igor.rodin.wordcounter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.igor.rodin.wordcounter.databinding.FragmentWordCounterBinding

class WordCounterFragment : Fragment() {
    var _binding: FragmentWordCounterBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

}