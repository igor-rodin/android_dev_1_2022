package ru.igor.rodin.m8_quiz_animation.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
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
        }

        initLottie(binding.animationView)
        animateUI()
        startLottieAnimate(binding.animationView)

        binding.restartQuiz.setOnClickListener {
            stopLottieAnimate(binding.animationView)
            findNavController().navigate(R.id.action_result_fragment_to_quiz_fragment)
        }
    }


    private fun initLottie(animationView: LottieAnimationView) {
        with(animationView) {
            setRepeatCount(LottieDrawable.INFINITE)
            setRepeatMode(LottieDrawable.RESTART)
            setAnimation(R.raw.lottie_anim)
        }
    }

    private fun startLottieAnimate(animationView: LottieAnimationView) {
        animationView.playAnimation()
    }

    private fun stopLottieAnimate(animationView: LottieAnimationView) {
        animationView.pauseAnimation()
    }

    private fun animateUI() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 2f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f, 1f)

        ObjectAnimator.ofPropertyValuesHolder(binding.resultTitle, scaleX, scaleY).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        ObjectAnimator.ofFloat(binding.quizResult, "rotation", 360f).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

    }
}