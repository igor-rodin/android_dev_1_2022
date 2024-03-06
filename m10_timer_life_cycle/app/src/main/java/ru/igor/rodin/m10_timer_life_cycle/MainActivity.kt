package ru.igor.rodin.m10_timer_life_cycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.igor.rodin.m10_timer_life_cycle.databinding.ActivityMainBinding
import java.time.Duration


private const val MAX_CDT_VALUE = 60f
private const val CDT_STEP_SIZE = 10f

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val timer = CountDownTimer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val timerState = savedInstanceState?.getParcelable(KEY_STATE) ?: CDTState.DEFAULT
        Log.d("TIMER-ON-CREATE", timerState.toString())
        initUI(timerState)

        with(binding) {
            timer.apply {
                duration = timerState.duration
                currentValue = timerState.currentValue * 1000
                setOnFinishAction {
                    Log.d("TIMER_FINISH", timer.currentValue.toString())
                    updateProgress(timer.currentValue)
                    timerSeekBar.isEnabled = true
                    startButton.isEnabled = true
                }

                setOnTickAction { time ->
                    Log.d("TIMER", "${time / 1000}")
                    updateProgress(time)
                    timerSeekBar.isEnabled = false
                }

                savedInstanceState?.let {
                    if (!timerState.isUIEnabled) start()
                }
            }
            startButton.isEnabled = timerState.isUIEnabled
            startButton.setOnClickListener {
                timer.start()
                it.isEnabled = false
            }
        }


    }

    private fun ActivityMainBinding.updateProgress(time: Long) {
        val timeInSeconds = time / 1000
        cdtValue.text = timeInSeconds.toString()
        timerProgress.progress = timeInSeconds.toInt()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val timerState =
            CDTState(
                timer.duration,
                timer.currentValue / 1000,
                binding.timerSeekBar.isEnabled
            )
        Log.d("TIMER-ON-SAVE_INSTANCE_STATE", timerState.toString())
        outState.putParcelable(KEY_STATE, timerState)
    }

    private fun initUI(timerState: CDTState) {
        with(binding) {
            timerSeekBar.apply {
                valueFrom = MIN_CDT_VALUE
                valueTo = MAX_CDT_VALUE
                stepSize = CDT_STEP_SIZE
                value = (timerState.duration.toMillis() / 1000).toFloat()
                isEnabled = timerState.isUIEnabled

                addOnChangeListener { _, value, _ ->
                    timerProgress.max = value.toInt()
                    timerProgress.progress = value.toInt()
                    cdtValue.text = value.toInt().toString()
                    timer.resetTo(Duration.ofSeconds(value.toLong()))
                }
            }
            Log.d("TIMER_INIT_UI", timerState.toString())
            timerProgress.apply {
                max = timerState.duration.toMillis().toInt() / 1000
                progress = timerState.currentValue.toInt()
            }

            cdtValue.text = timerState.currentValue.toString()

//            timer.apply {
//                setDuration(Duration.ofSeconds(timerProgress.max.toLong()))
//                setOnFinishAction {
//                    timerSeekBar.isEnabled = true
//                }
//            }
//
//            startButton.setOnClickListener {
//                timer.start {
//                    Log.d("TIMER", "${it / 1000}")
//                    cdtValue.text = (it / 1000).toString()
//                    timerProgress.progress = (it / 1000).toInt()
//                    timerSeekBar.isEnabled = false
//                }
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.stop()
        _binding = null
    }

    companion object {
        private const val KEY_STATE = "TimerState"
        const val MIN_CDT_VALUE = 10f
    }
}