package ru.igor.rodin.m7_quiz_fragments.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.allViews
import ru.igor.rodin.m7_quiz_fragments.R
import ru.igor.rodin.m7_quiz_fragments.databinding.ViewQuestionBinding

typealias onQuestionCheckedListener =  (checkedIdx: Int, checkedButton: RadioButton) -> Unit
class QuestionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewQuestionBinding;
    private var listener: onQuestionCheckedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_question, this, true)
        binding = ViewQuestionBinding.bind(this)
        applyCustomAttr(context, attrs)
        initListeners()
    }

    private fun initListeners() {
        binding.questionGroup.setOnCheckedChangeListener { radioGroup, id ->
            run {
                val button = findViewById<RadioButton>(id)
                val idx = radioGroup.indexOfChild(button)
                listener?.invoke(idx - 1, button)
            }
        }
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

    fun setAnswers(values: List<String>) {
        if (values.isEmpty()) {
            return
        }
        binding.root.allViews.filter { v -> v is RadioButton }
            .forEachIndexed { idx, v -> (v as RadioButton).text = values[idx] }

    }

    fun setOnCheckedQuestionListener(listener: onQuestionCheckedListener?) {
        this.listener = listener
    }
}