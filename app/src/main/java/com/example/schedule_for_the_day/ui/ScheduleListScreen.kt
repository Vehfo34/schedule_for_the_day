package com.example.schedule_for_the_day.ui

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule_for_the_day.ui.components.EventCard
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel


@Composable
fun ScheduleListScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("editor?eventId=-1")}) {
                Icon(Icons.Default.Add, contentDescription = "Добавить событие")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(events) { event ->
                EventCard(
                    title = event.title,
                    time = event.time,
                    modifier = Modifier.clickable {
                        navController.navigate("editor?eventId=${event.id}")
                    }
                )
            }
        }
    }
}