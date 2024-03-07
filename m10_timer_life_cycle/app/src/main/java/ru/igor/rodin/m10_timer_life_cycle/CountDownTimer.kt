package ru.igor.rodin.m10_timer_life_cycle

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive

typealias OnFinishAction = () -> Unit
typealias OnTickAction = (Long) -> Unit

/**
 *  CountDownTimer
 *  [duration] - duration of timer
 *  [tickInterval] - interval between ticks
 */
class CountDownTimer(
    var duration: Duration = Duration.ofSeconds(60),
    private val tickInterval: Duration = Duration.ofSeconds(1),
) {
    private var state: State = State.STOPPED
    private val scope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null
    private var onFinishAction: OnFinishAction? = null
    private var onTickAction: OnTickAction? = null
    var currentValue: Long = duration.toMillis()

    fun getState() = state
    fun setOnFinishAction(onStopAction: OnFinishAction) {
        this.onFinishAction = onStopAction
    }

    fun setOnTickAction(onTickAction: OnTickAction) {
        this.onTickAction = onTickAction
    }
    fun start() {
        state = when (state) {
            State.STOPPED -> State.RUNNING
            State.RUNNING -> State.PAUSED
            else -> State.RUNNING
        }

        job = scope.launch() {
            while (isActive) {
                if (currentValue < 0) {
                    job?.cancel()
                    onFinishAction?.invoke()
                    return@launch
                }
                onTickAction?.invoke(currentValue)
                currentValue -= tickInterval.toMillis()
                delay(tickInterval.toMillis())

            }
        }
    }

    fun stop() {
        state = State.PAUSED
        job?.cancel()
    }

    fun resetTo(duration: Duration) {
        state = State.STOPPED
        this.duration = duration
        currentValue = duration.toMillis()
    }

    enum class State {
        RUNNING, PAUSED, STOPPED
    }
}
