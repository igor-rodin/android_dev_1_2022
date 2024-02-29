package ru.igor.rodin.m8_quiz_animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.igor.rodin.m8_quiz_animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}