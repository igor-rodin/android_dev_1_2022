package ru.igor.rodin.m9_quiz_localization.ui


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import ru.igor.rodin.m9_quiz_localization.R
import ru.igor.rodin.m9_quiz_localization.databinding.FragmentWelcomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar


private const val BIRTH_DATE_DIALOG = "birthDateDialog"

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chooseBirthdate?.setOnClickListener { showChooseBirthdate() }

        binding.startQuiz?.setOnClickListener {
            findNavController().navigate(R.id.action_welcome_fragment_to_quiz_fragment)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showChooseBirthdate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(getString(R.string.date_format))

        val birthDAteDialog =
            MaterialDatePicker.Builder.datePicker().setTitleText(R.string.birthdate_dialog_title)
                .build().apply {
                    addOnPositiveButtonClickListener { timestamp ->
                        calendar.timeInMillis = timestamp
                        val formattedDate = getString(
                            R.string.snakebar_birthdate_message,
                            dateFormat.format(calendar.time)
                        )
                        Snackbar.make(
                            binding.chooseBirthdate!!,
                            formattedDate,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

        birthDAteDialog.show(
            parentFragmentManager,
            BIRTH_DATE_DIALOG
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}