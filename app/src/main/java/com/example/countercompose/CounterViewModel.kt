package com.example.countercompose

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class CounterViewModel(
    application: Application
) : AndroidViewModel(application) {

    // 1. Membuka/membuat file SharedPreferences
    private val prefs = application.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // 2. State awal dibaca dari SharedPreferences (default 0)
    private val _counter = mutableIntStateOf(
        prefs.getInt(KEY_COUNTER, 0)
    )
    val counter: State<Int> = _counter

    // 3. Fungsi yang dipanggil dari UI
    fun increment() = updateCounter(_counter.intValue + 1)
    fun decrement() = updateCounter(_counter.intValue - 1)
    fun reset() = updateCounter(0)

    // 4. Ubah state lalu simpan ke SharedPreferences
    private fun updateCounter(newValue: Int) {
        _counter.intValue = newValue
        prefs.edit { putInt(KEY_COUNTER, newValue) }
    }

    companion object {
        private const val PREFS_NAME = "counter_prefs"
        private const val KEY_COUNTER = "counter_value"
    }
}