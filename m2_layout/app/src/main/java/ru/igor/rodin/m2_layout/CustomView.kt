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
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_custom, this, true)
        binding = ViewCustomBinding.bind(this)
    }

    fun setTopLineText(value: CharSequence) {
        binding.topLine.text = value
    }

    fun setBottomLineText(value: CharSequence) {
        binding.bottomLine.text = value
    }
}