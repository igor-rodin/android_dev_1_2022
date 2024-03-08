package ru.igor.rodin.m10_timer_life_cycle

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive

typealias OnFinishAction = () -> Unit
typealias OnTickAction = (Long) -> Unit

/**
 *  CountDownTimer
 *  [duration] - duration of timer
 *  [tickInterval] - interval between ticks
 */

private const val DEFAULT_DURATION_SEC = 60L
private const val DEFAULT_TICK_INTERVAL_SEC = 1L
class CountDownTimer(
    var duration: Duration = Duration.ofSeconds(DEFAULT_DURATION_SEC),
    private var tickInterval: Duration = Duration.ofSeconds(DEFAULT_TICK_INTERVAL_SEC),
) {
    private val scope = CoroutineScope(Dispatchers.Main)
    private  var timerJob: Job? = null
    private var onFinishAction: OnFinishAction? = null
    private var onTickAction: OnTickAction? = null
    private var state: State = State.STOPPED
    var currentValue: Long = duration.toMillis()


    fun getTickInterval() = tickInterval
    fun setTickInterval(tickInterval: Duration) {
        if(tickInterval.toMillis() <= 0 || tickInterval.toMillis() > duration.toMillis()) {
            this.tickInterval = Duration.ofSeconds(DEFAULT_TICK_INTERVAL_SEC)
        } else  {
            this.tickInterval = tickInterval
        }
    }
    fun getState() = state
    fun setState(state: State) {
        this.state = state
    }

    /**
     * Set listener [OnFinishAction] for timer finish
     */
    fun setOnFinishAction(onFinishAction: OnFinishAction) {
        this.onFinishAction = onFinishAction
    }

    /**
     * Set listener [OnTickAction] for timer tick action
     */
    fun setOnTickAction(onTickAction: OnTickAction) {
        this.onTickAction = onTickAction
    }

    /**
     * Start timer
     */
    fun start() {
        state = when (state) {
            State.STOPPED -> State.RUNNING
            State.RUNNING -> State.PAUSED
            else -> State.RUNNING
        }

        timerJob = scope.launch() {
            while (isActive) {
                if (currentValue < 0) {
                    timerJob?.cancel()
                    onFinishAction?.invoke()
                    return@launch
                }
                onTickAction?.invoke(currentValue)
                currentValue -= tickInterval.toMillis()
                delay(tickInterval.toMillis())
            }
        }
    }

    /**
     * Stop timer on pause
     */
    fun stop() {
        state = State.PAUSED
        timerJob?.cancel()
    }

    /**
     * Resuming timer after pause
     */
    fun resume() {
        state = State.PAUSED
        start()
    }

    /**
     * Reset timer state to [State.STOPPED] and current timer value to [duration]
     */
    fun resetTo(duration: Duration) {
        state = State.STOPPED
        this.duration = duration
        currentValue = duration.toMillis()
    }

    enum class State {
        RUNNING, PAUSED, STOPPED
    }
}
