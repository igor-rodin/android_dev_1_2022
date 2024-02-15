package ru.igor.rodin.m4_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import ru.igor.rodin.m4_components.databinding.ActivityMainBinding
import kotlin.random.Random

private const val MAX_POINTS = 100

private const val MAX_NAME_SIZE = 40

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setupProfileComponents()
    }

    private fun setupProfileComponents() {
        initPointsBar()
        with(binding) {
            textEditName.doAfterTextChanged { updateBtnState() }
            textEditPhone.doAfterTextChanged { updateBtnState() }
            notifications.setOnCheckedChangeListener { _, isChecked ->
                enableNotifications(isChecked)
            }
            notifyAuth.setOnCheckedChangeListener { _, _ -> updateBtnState() }
            notifyNews.setOnCheckedChangeListener { _, _ -> updateBtnState() }
            btnSave.isEnabled = false
            btnSave.setOnClickListener { saveProfile() }
        }
    }

    private fun enableNotifications(isChecked: Boolean) {
        with(binding) {
            notifyAuth.isEnabled = isChecked
            notifyNews.isEnabled = isChecked
        }
        updateBtnState()
    }

    private fun initPointsBar() {
        val currentPoints = Random.nextInt(MAX_POINTS + 1)
        with(binding) {
            profilePoints.text = getString(R.string.profile_points, currentPoints, MAX_POINTS)
            points.max = MAX_POINTS
            points.setProgress(currentPoints, true)
        }
    }

    private fun updateBtnState() {
        with(binding) {
            btnSave.isEnabled = isNameValid() && isPhoneValid() && isNotificationStateIsValid()
        }
    }

    private fun isNameValid(): Boolean {
        with(binding) {
            return textEditName.text?.isNotBlank() ?: false && textEditName.text?.length!! <= MAX_NAME_SIZE
        }
    }

    private fun isPhoneValid(): Boolean = binding.textEditPhone.text?.isNotBlank() ?: false

    private fun isNotificationStateIsValid(): Boolean =
        with(binding) { return !notifications.isChecked || (notifications.isChecked && (notifyAuth.isChecked || notifyNews.isChecked)) }

    private fun saveProfile() {
        Snackbar.make(binding.root, getString(R.string.save_message), Snackbar.LENGTH_SHORT).show()
    }
}