package com.example.schedule_for_the_day.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schedule_for_the_day.ui.EditorScreen
import com.example.schedule_for_the_day.ui.MonthScreen
import com.example.schedule_for_the_day.ui.ScheduleListScreen
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel


@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val viewModel: ScheduleViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (currentRoute == "list" || currentRoute == "month") {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "list",
                        onClick = { navController.navigate("list")},
                        icon = { Icon(Icons.Default.List, contentDescription = null)},
                        label = {Text("Список")}
                    )
                    NavigationBarItem(
                        selected = currentRoute == "month",
                        onClick = { navController.navigate("month")},
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null)},
                        label = {Text("Месяц")}
                    )
                }

            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") { ScheduleListScreen(navController, viewModel) }
            composable("month") { MonthScreen(navController) }
            composable(
                "editor?eventId={eventId}",
                arguments = listOf(
                    navArgument("eventId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) {
                    backStackEntry ->
                val eventId = backStackEntry.arguments?.getLong("eventId") ?: -1L
                EditorScreen(eventId = eventId, navController = navController, viewModel = viewModel)
            }
        }
    }
}
