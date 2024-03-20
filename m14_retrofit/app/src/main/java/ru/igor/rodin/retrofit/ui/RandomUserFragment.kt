package ru.igor.rodin.retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.igor.rodin.retrofit.databinding.FragmentRandomUserBinding


class RandomUserFragment : Fragment() {

    private var _binding: FragmentRandomUserBinding? = null
    private val binding get() = _binding!!
    private val randomUserViewModel: RandomUserViewModel by viewModels {RandomUserViewModel.Factory}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}