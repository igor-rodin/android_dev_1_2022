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

        val timerSavedState = savedInstanceState?.getParcelable(KEY_STATE) ?: CDTState.DEFAULT
        initUI(timerSavedState)
        initTimer(timerSavedState)
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
    private fun initUI(timerState: CDTState) = binding.apply {
        timerSeekBar.apply {
            valueFrom = MIN_CDT_VALUE
            valueTo = MAX_CDT_VALUE
            stepSize = CDT_STEP_SIZE
            value = (timerState.duration.toMillis() / ONE_SECOND).toFloat()
            isEnabled = timerState.state == CountDownTimer.State.STOPPED

            addOnChangeListener { _, value, _ ->
                initTimeProgressState(value)
            }
        }
        timerProgress.apply {
            max = timerState.duration.toMillis().toInt() / 1000
            progress = timerState.currentValue.toInt()
        }

        cdtValue.text = timerState.currentValue.toString()

        updateControlsState(timerState.state)

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
    private fun updateControlsState(state: CountDownTimer.State): Unit = with(binding) {
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
    private fun stopTimer() {
        timer.stop()
        timer.resetTo(timer.duration)
        updateControlsState(timer.getState())
        updateTimerProgress(timer.duration.toMillis())
    }

    /**
     * Pause timer and update UI
     */
    private fun pauseTimer() {
        timer.stop()
        updateControlsState(timer.getState())
    }

    /**
     * Start or resume timer
     */
    private fun startOrResumeTimer() {
        timer.start()
        updateControlsState(timer.getState())
    }

    /**
     * Initialize timer with saved state
     */
    private fun initTimer(timerState: CDTState) = timer.apply {
        setState(timerState.state)
        duration = timerState.duration
        currentValue = timerState.currentValue * 1000

        setOnFinishAction {
            timer.resetTo(timer.duration)
            updateTimerProgress(timer.currentValue)
            updateControlsState(timer.getState())
        }

        setOnTickAction { time ->
            updateTimerProgress(time)
            binding.timerSeekBar.isEnabled = false
        }
        if (timerState.state == CountDownTimer.State.RUNNING) resume()
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