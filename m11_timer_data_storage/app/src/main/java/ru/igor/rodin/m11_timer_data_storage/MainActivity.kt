package ru.igor.rodin.m11_timer_data_storage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.igor.rodin.m11_timer_data_storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val repository = Repository(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
    }

    private fun initUI() {
        binding.btnClean.setOnClickListener {
            cleanText()
        }
        binding.btnSave.setOnClickListener {
            saveText()
        }
        updateSavedText()
    }

    private fun cleanText() {
        repository.clearText()
        updateSavedText()
    }

    private fun updateSavedText() {
        binding.message.text = repository.getText()
    }

    private fun saveText() {
        repository.saveText(binding.editText.text.toString())
        updateSavedText()
    }
}