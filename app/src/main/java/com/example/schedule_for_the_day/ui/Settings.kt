package com.example.schedule_for_the_day.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule_for_the_day.viewmodel.SettingsViewModel


@Composable
fun Settings(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Карточка "Тема"
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Тема", fontSize = 24.sp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    // Светлая
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Светлая")
                        Switch(
                            checked = !isDarkTheme,
                            onCheckedChange = { checked ->
                                if (checked) settingsViewModel.setDarkTheme(false)
                            }
                        )
                    }
                    // Тёмная
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Тёмная")
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { checked ->
                                if (checked) settingsViewModel.setDarkTheme(true)
                            }
                        )
                    }
                }
            }
        }

        // Карточка "Язык"
        // Карточка "Язык"
        Card(modifier = Modifier.fillMaxWidth()) {
            var isRussianOn by remember { mutableStateOf(false) }
            var isEnglishOn by remember { mutableStateOf(true) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Язык", fontSize = 24.sp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    // Русский
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Русский")
                        Switch(
                            checked = isRussianOn,
                            onCheckedChange = { checked ->
                                isRussianOn = checked
                                isEnglishOn = !checked
                            }
                        )
                    }
                    // Английский
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Английский")
                        Switch(
                            checked = isEnglishOn,
                            onCheckedChange = { checked ->
                                isEnglishOn = checked
                                isRussianOn = !checked
                            }
                        )
                    }
                }
            }
        }
    }
}












