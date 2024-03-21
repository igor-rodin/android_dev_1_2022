package ru.igor.rodin.m13_databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.igor.rodin.m13_databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}