package ru.igor.rodin.m8_quiz_animation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import ru.igor.rodin.m8_quiz_animation.R
import ru.igor.rodin.m8_quiz_animation.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startQuiz?.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_fragment_to_quiz_fragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}