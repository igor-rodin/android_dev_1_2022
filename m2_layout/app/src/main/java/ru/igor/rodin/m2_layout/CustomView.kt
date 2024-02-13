package ru.igor.rodin.m2_layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import ru.igor.rodin.m2_layout.databinding.ViewCustomBinding

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewCustomBinding;

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom, this, true)
        binding = ViewCustomBinding.bind(this)
        applyCustomAttr(context, attrs)
    }

    private fun applyCustomAttr(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0).apply {
            try {
                getString(R.styleable.CustomView_topLineText)?.let { setTopLineText(it) }
                getString(R.styleable.CustomView_bottomLineText)?.let { setBottomLineText(it) }
            } finally {
                recycle()
            }
        }
    }

    fun setTopLineText(value: CharSequence) {
        binding.topLine.text = value
    }

    fun setBottomLineText(value: CharSequence) {
        binding.bottomLine.text = value
    }
}