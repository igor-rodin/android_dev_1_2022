package ru.igor.rodin.m10_timer_life_cycle

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
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

        val timerSavedState = savedInstanceState?.getParcelable(KEY_STATE) ?: CDTState.DEFAULT

        initUI(timerSavedState)

        initTimer(timerSavedState)
    }

    private fun ActivityMainBinding.updateTimerProgress(time: Long) {
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
                state = timer.getState(),
            )
        outState.putParcelable(KEY_STATE, timerState)
    }

    private fun initUI(timerState: CDTState) {
        with(binding) {
            timerSeekBar.apply {
                valueFrom = MIN_CDT_VALUE
                valueTo = MAX_CDT_VALUE
                stepSize = CDT_STEP_SIZE
                value = (timerState.duration.toMillis() / 1000).toFloat()
                isEnabled = timerState.state == CountDownTimer.State.STOPPED

                addOnChangeListener { _, value, _ ->
                    timerProgress.max = value.toInt()
                    timerProgress.progress = value.toInt()
                    cdtValue.text = value.toInt().toString()
                    timer.resetTo(Duration.ofSeconds(value.toLong()))
                }
            }
            timerProgress.apply {
                max = timerState.duration.toMillis().toInt() / 1000
                progress = timerState.currentValue.toInt()
            }

            cdtValue.text = timerState.currentValue.toString()

            when (timerState.state) {
                CountDownTimer.State.STOPPED, CountDownTimer.State.PAUSED -> {
                    startButton.visibility = VISIBLE
                    pauseButton.visibility = GONE
                }

                CountDownTimer.State.RUNNING -> {
                    startButton.visibility = GONE
                    pauseButton.visibility = VISIBLE
                }
            }

        }
    }

    private fun initTimer(timerState: CDTState) {
        with(binding) {
            timer.apply {
                duration = timerState.duration
                currentValue = timerState.currentValue * 1000
                setOnFinishAction {
                    timer.resetTo(timerState.duration)
                    updateTimerProgress(timer.currentValue)
                    timerSeekBar.isEnabled = true
                    startButton.visibility = VISIBLE
                    pauseButton.visibility = GONE
                }

                setOnTickAction { time ->
                    updateTimerProgress(time)
                    timerSeekBar.isEnabled = false
                }

                startButton.setOnClickListener {
                    timer.start()
                    it.visibility = GONE
                    pauseButton.visibility = VISIBLE
                }
                pauseButton.setOnClickListener {
                    timer.stop()
                    it.visibility = GONE
                    startButton.visibility = VISIBLE
                }
            }
        }
        if (timerState.state == CountDownTimer.State.RUNNING) timer.start()
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