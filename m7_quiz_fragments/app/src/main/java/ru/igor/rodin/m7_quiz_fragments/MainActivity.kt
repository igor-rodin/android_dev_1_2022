package ru.igor.rodin.m7_quiz_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.igor.rodin.m7_quiz_fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}