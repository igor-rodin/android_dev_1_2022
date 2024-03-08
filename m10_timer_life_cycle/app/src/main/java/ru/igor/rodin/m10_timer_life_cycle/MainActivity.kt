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
private const val ONE_SECOND = 1000

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

        (savedInstanceState?.getParcelable(KEY_STATE) ?: CDTState.DEFAULT).apply {
            initUI(this)
            initTimer(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val timerState =
            CDTState(
                timer.duration,
                timer.currentValue / ONE_SECOND,
                state = timer.getState(),
            )
        outState.putParcelable(KEY_STATE, timerState)
    }

    /**
     * Initialize UI with saved state
     */
    private fun initUI(savedTimerState: CDTState) = binding.apply {
        timerSeekBar.apply {
            valueFrom = MIN_CDT_VALUE
            valueTo = MAX_CDT_VALUE
            stepSize = CDT_STEP_SIZE
            value = (savedTimerState.duration.toMillis() / ONE_SECOND).toFloat()
            isEnabled = savedTimerState.state == CountDownTimer.State.STOPPED

            addOnChangeListener { _, value, _ ->
                initTimeProgressState(value)
            }
        }
        timerProgress.apply {
            max = savedTimerState.duration.toMillis().toInt() / ONE_SECOND
            progress = savedTimerState.currentValue.toInt()
        }

        cdtValue.text = savedTimerState.currentValue.toString()

        updateControlsState(savedTimerState.state)

        startButton.setOnClickListener {
            startOrResumeTimer()
        }
        pauseButton.setOnClickListener {
            pauseTimer()
        }

        cancelButton.setOnClickListener {
            stopTimer()
        }
    }

    /**
     * Update timer progress on every timer's tick
     */
    private fun updateTimerProgress(time: Long) = binding.apply {
        val timeInSeconds = time / ONE_SECOND
        cdtValue.text = timeInSeconds.toString()
        timerProgress.progress = timeInSeconds.toInt()
    }

    /**
     * Initialize time progress bar state with timer duration [duration]
     */
    private fun initTimeProgressState(duration: Float) = binding.apply {
        timerProgress.max = duration.toInt()
        updateTimerProgress(duration.toLong() * ONE_SECOND)
        timer.resetTo(Duration.ofSeconds(duration.toLong()))
    }

    /**
     * Update buttons state depending on timer state
     */
    private fun updateControlsState(state: CountDownTimer.State) = binding.apply {
        when (state) {
            CountDownTimer.State.STOPPED -> {
                startButton.visibility = VISIBLE
                pauseButton.visibility = GONE
                cancelButton.visibility = GONE
                timerSeekBar.isEnabled = true
            }

            CountDownTimer.State.PAUSED -> {
                startButton.visibility = VISIBLE
                pauseButton.visibility = GONE
                cancelButton.visibility = VISIBLE
            }

            CountDownTimer.State.RUNNING -> {
                startButton.visibility = GONE
                pauseButton.visibility = VISIBLE
                cancelButton.visibility = VISIBLE
            }
        }
    }


    /**
     * Stop timer and update UI
     */
    private fun stopTimer() = timer.apply {
        stop()
        resetTo(duration)
        updateControlsState(getState())
        updateTimerProgress(duration.toMillis())
    }

    /**
     * Pause timer and update UI
     */
    private fun pauseTimer() = timer.apply {
        stop()
        updateControlsState(getState())
    }

    /**
     * Start or resume timer
     */
    private fun startOrResumeTimer() = timer.apply {
        start()
        updateControlsState(getState())
    }

    /**
     * Initialize timer with saved state
     */
    private fun initTimer(timerSavedState: CDTState) = timer.apply {
        setState(timerSavedState.state)
        duration = timerSavedState.duration
        currentValue = timerSavedState.currentValue * ONE_SECOND

        setOnFinishAction {
            resetTo(duration)
            updateTimerProgress(currentValue)
            updateControlsState(getState())
        }

        setOnTickAction { time ->
            updateTimerProgress(time)
            binding.timerSeekBar.isEnabled = false
        }
        if (timerSavedState.state == CountDownTimer.State.RUNNING) resume()
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