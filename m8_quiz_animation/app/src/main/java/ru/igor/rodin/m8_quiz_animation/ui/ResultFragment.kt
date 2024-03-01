package ru.igor.rodin.m8_quiz_animation.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import ru.igor.rodin.m8_quiz_animation.R
import ru.igor.rodin.m8_quiz_animation.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(QuizFragment.QUIZ_RESULT).let {
            binding.quizResult.text = it
            binding.quizResult.translationX
        }

        animateUI()

        binding.restartQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_result_fragment_to_quiz_fragment)
        }
    }

    private fun animateUI() {
        ObjectAnimator.ofFloat(binding.resultTitle, "scaleY", 1f, 2f, 1f).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        ObjectAnimator.ofFloat(binding.quizResult, "alpha", 0f, 1f).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

}