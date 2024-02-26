package ru.igor.rodin.m7_quiz_fragments.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.core.view.children
import ru.igor.rodin.m7_quiz_fragments.R
import ru.igor.rodin.m7_quiz_fragments.databinding.ViewQuestionBinding

class QuestionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewQuestionBinding;

    init {
        LayoutInflater.from(context).inflate(R.layout.view_question, this, true)
        binding = ViewQuestionBinding.bind(this)
        applyCustomAttr(context, attrs)
    }

    private fun applyCustomAttr(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.QuestionView, 0, 0).apply {
            try {
                getString(R.styleable.QuestionView_question)?.let { setQuestion(it) }
            } finally {
                recycle()
            }
        }
    }

    fun setQuestion(value: CharSequence) {
        binding.questionLabel.text = value
    }

    fun setAnswers(value: List<String>) {
        if (value.isEmpty()) {
            return
        }
        binding.root.allViews.filter { v -> v is RadioButton }
            .forEachIndexed { idx, v -> (v as RadioButton).text = value[idx] }

    }
}