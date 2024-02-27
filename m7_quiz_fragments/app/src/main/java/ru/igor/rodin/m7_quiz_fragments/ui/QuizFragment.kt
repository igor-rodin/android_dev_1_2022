package ru.igor.rodin.m7_quiz_fragments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import ru.igor.rodin.m7_quiz_fragments.R
import ru.igor.rodin.m7_quiz_fragments.databinding.FragmentQuizBinding
import ru.igor.rodin.m7_quiz_fragments.quiz.QuizStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentQuizBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        with(binding){
            fillQuiz(QuizStorage.Locale.Ru)

            back.setOnClickListener {
                findNavController().navigate(R.id.action_quiz_fragment_to_welcome_fragment)
            }
            sendQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_quiz_fragment_to_resultFragment)
            }
        }



    }

    private fun FragmentQuizBinding.fillQuiz(locale: QuizStorage.Locale) {
        val quizQuestions = QuizStorage.getQuiz(locale).questions

        questions.children.filter { v -> v is QuestionView }.forEachIndexed { idx, view ->
            (view as QuestionView).apply {
                setQuestion("1. ${quizQuestions[idx].question}")
                setAnswers(quizQuestions[idx].answers)
                setOnCheckedQuestionListener { checkedIdx, _ -> Toast.makeText(context, checkedIdx.toString(), Toast.LENGTH_SHORT).show() }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}