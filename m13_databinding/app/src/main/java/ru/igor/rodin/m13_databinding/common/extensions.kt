package ru.igor.rodin.m13_databinding.common

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun EditText.getTextFlow(): Flow<CharSequence?> = callbackFlow {
    val callback = doOnTextChanged { text, _, _, _ -> trySend(text) }
    addTextChangedListener(callback)
    awaitClose { removeTextChangedListener(callback) }
}