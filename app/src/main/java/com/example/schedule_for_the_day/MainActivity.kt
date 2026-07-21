package com.example.schedule_for_the_day

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedule_for_the_day.ui.navigation.AppNavGraph
import com.example.schedule_for_the_day.ui.theme.Schedule_for_the_dayTheme
import com.example.schedule_for_the_day.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Получаем ViewModel на уровне Activity, чтобы состояние сохранялось
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            Schedule_for_the_dayTheme(
                darkTheme = isDarkTheme,   // берём актуальное состояние
                dynamicColor = false       // отключаем динамические цвета для чистоты переключения
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}