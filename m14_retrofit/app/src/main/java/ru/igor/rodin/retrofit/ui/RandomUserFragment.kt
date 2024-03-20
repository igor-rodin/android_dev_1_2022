package ru.igor.rodin.retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.igor.rodin.retrofit.databinding.FragmentRandomUserBinding


class RandomUserFragment : Fragment() {

    private var _binding: FragmentRandomUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomUserBinding.inflate(inflater, container, false)
        return binding.root
    }

}