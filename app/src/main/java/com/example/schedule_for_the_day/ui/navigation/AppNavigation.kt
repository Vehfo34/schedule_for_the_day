package com.example.schedule_for_the_day.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schedule_for_the_day.ui.EditorScreen
import com.example.schedule_for_the_day.ui.ScheduleListScreen
import com.example.schedule_for_the_day.ui.Settings
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel
import com.example.schedule_for_the_day.viewmodel.SettingsViewModel


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel  // <-- добавить параметр
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val viewModel: ScheduleViewModel = viewModel()

    Scaffold(
        topBar = {
            if (currentRoute == "Editing" || currentRoute == "Settings") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        TextButton(onClick = { navController.navigate("Editing") }) {
                            Icon(Icons.Default.List, contentDescription = null)
                            Text("Редактирование", fontSize = 15.sp)
                        }
                    }
                    Column(modifier = Modifier.padding(15.dp)) {
                        TextButton(onClick = { navController.navigate("Settings") }) {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                            Text("Настройки", fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Editing",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Editing") { ScheduleListScreen(navController, viewModel) }
            composable("Settings") {
                // Передаём settingsViewModel из параметра
                Settings(
                    navController = navController,
                    settingsViewModel = settingsViewModel
                )
            }
            composable(
                "editor?eventId={eventId}",
                arguments = listOf(
                    navArgument("eventId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getLong("eventId") ?: -1L
                EditorScreen(eventId = eventId, navController = navController, viewModel = viewModel)
            }
        }
    }
}