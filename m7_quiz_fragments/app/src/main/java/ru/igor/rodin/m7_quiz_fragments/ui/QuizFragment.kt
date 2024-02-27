package ru.igor.rodin.m7_quiz_fragments.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import ru.igor.rodin.m7_quiz_fragments.R
import ru.igor.rodin.m7_quiz_fragments.databinding.FragmentQuizBinding
import ru.igor.rodin.m7_quiz_fragments.quiz.Quiz
import ru.igor.rodin.m7_quiz_fragments.quiz.QuizStorage

private const val QUIZ_RESULT = "quizResult"

class QuizFragment : Fragment() {
    private lateinit var quiz: Quiz
    private val userChoice: MutableMap<Int, Int> = mutableMapOf()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quiz = getQuizForLocale()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            fillQuiz(quiz)

            back.setOnClickListener {
                findNavController().navigate(R.id.action_quiz_fragment_to_welcome_fragment)
            }
            sendQuiz.setOnClickListener {
                if (userChoice.size != quiz.questions.size) {
                    showWarningMessage(getString(R.string.should_all_answers_warning_mesage))
                } else {
                    val messageResult =
                        QuizStorage.answer(quiz, userChoice.values.toList())
                    findNavController().navigate(
                        R.id.action_quiz_fragment_to_resultFragment,
                        bundleOf(QUIZ_RESULT to messageResult)
                    )
                }
            }
        }


    }

    private fun getQuizForLocale(locale: QuizStorage.Locale? = null): Quiz {
        if (locale != null){
            return QuizStorage.getQuiz(locale)
        }
        return when (resources.configuration.locales.get(0).toLanguageTag()) {
            "ru-RU" -> QuizStorage.getQuiz(QuizStorage.Locale.Ru)
            else -> QuizStorage.getQuiz(QuizStorage.Locale.En)
        }
    }
    private fun showWarningMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun FragmentQuizBinding.fillQuiz(quiz: Quiz) {
        val quizQuestions = quiz.questions

        questions.children.filter { v -> v is QuestionView }.forEachIndexed { idx, view ->
            (view as QuestionView).apply {
                setQuestion("${idx + 1}. ${quizQuestions[idx].question}")
                setAnswers(quizQuestions[idx].answers)
                setOnCheckedQuestionListener {groupTag, checkedIdx, _ ->
                    updateUserChoice(groupTag, checkedIdx)
                }
            }
        }
    }

    private fun updateUserChoice(groupTag: Int, checkedIdx: Int) {
        userChoice[groupTag] = checkedIdx
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}