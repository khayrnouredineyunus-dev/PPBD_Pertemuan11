package com.example.countercompose

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class CounterViewModel(
    application: Application
) : AndroidViewModel(application) {

    // 1. Membuka/membuat file SharedPreferences
    private val prefs = application.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // 2. State counter, nilai awal dibaca dari SharedPreferences
    private val _counter = mutableIntStateOf(
        prefs.getInt(KEY_COUNTER, 0)
    )
    val counter: State<Int> = _counter

    // 3. [CHALLENGE] State teks yang sedang diketik di form "Nama"
    private val _nameInput = mutableStateOf("")
    val nameInput: State<String> = _nameInput

    // 4. [CHALLENGE] State nama yang sudah disimpan (tampil di bawah counter)
    private val _savedName = mutableStateOf(
        prefs.getString(KEY_NAME, "") ?: ""
    )
    val savedName: State<String> = _savedName

    // 5. Fungsi counter yang dipanggil dari UI
    fun increment() = updateCounter(_counter.intValue + 1)
    fun decrement() = updateCounter(_counter.intValue - 1)
    fun reset() = updateCounter(0)

    // 6. [CHALLENGE] Dipanggil setiap kali isi form berubah (tiap huruf diketik)
    fun onNameChange(newName: String) {
        _nameInput.value = newName
    }

    // 7. [CHALLENGE] Dipanggil saat tombol SIMPAN diklik
    fun saveName() {
        val name = _nameInput.value.trim()
        _savedName.value = name
        prefs.edit { putString(KEY_NAME, name) }
    }

    private fun updateCounter(newValue: Int) {
        _counter.intValue = newValue
        prefs.edit { putInt(KEY_COUNTER, newValue) }
    }

    companion object {
        private const val PREFS_NAME = "counter_prefs"
        private const val KEY_COUNTER = "counter_value"
        private const val KEY_NAME = "saved_name"
    }
}