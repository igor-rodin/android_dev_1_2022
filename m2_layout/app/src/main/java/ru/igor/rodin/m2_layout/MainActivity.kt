package ru.igor.rodin.m2_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.igor.rodin.m2_layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initText()
    }

    private fun initText() {
        with(binding.customView){
            setTopLineText(getText(R.string.top_line_text))
            setBottomLineText(getText(R.string.bottom_line_text))
        }
    }
}