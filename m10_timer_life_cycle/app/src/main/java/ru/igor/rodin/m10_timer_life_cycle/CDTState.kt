package ru.igor.rodin.m10_timer_life_cycle

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Duration

@Parcelize
data class CDTState(
    val duration: Duration,
    val currentValue: Long,
    val isUIEnabled: Boolean,
) :
    Parcelable {
    companion object {
        val DEFAULT = CDTState(
            Duration.ofSeconds(MainActivity.MIN_CDT_VALUE.toLong()),
            MainActivity.MIN_CDT_VALUE.toLong(),
            isUIEnabled = true,
        )
    }
}
