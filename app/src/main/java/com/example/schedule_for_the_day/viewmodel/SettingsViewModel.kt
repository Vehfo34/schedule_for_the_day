package com.example.schedule_for_the_day.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("settings", android.content.Context.MODE_PRIVATE)

    // Тема: false = светлая (по умолчанию), true = тёмная
    private val _isDarkTheme = MutableStateFlow(prefs.getBoolean("dark_theme", false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setDarkTheme(dark: Boolean) {
        _isDarkTheme.value = dark
        prefs.edit().putBoolean("dark_theme", dark).apply()
    }

    // Язык: false = английский (по умолчанию), true = русский
    private val _isRussian = MutableStateFlow(prefs.getBoolean("russian_lang", false))
    val isRussian: StateFlow<Boolean> = _isRussian.asStateFlow()

    fun setLanguage(russian: Boolean) {
        _isRussian.value = russian
        prefs.edit().putBoolean("russian_lang", russian).apply()
    }
}