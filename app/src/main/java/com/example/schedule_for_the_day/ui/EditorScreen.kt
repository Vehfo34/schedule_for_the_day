package com.example.schedule_for_the_day.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel

fun parseTimeRange(timeRange: String): List<String> {
    val parts = timeRange.split(" - ")
    val start = parts.getOrElse(0) { "" }.split(":")
    val end = parts.getOrElse(1) { "" }.split(":")
    return listOf(
        start.getOrElse(0) { "" },  // startHours
        start.getOrElse(1) { "" },  // startMinutes
        end.getOrElse(0) { "" },    // endHours
        end.getOrElse(1) { "" }     // endMinutes
    )
}

@Composable
fun EditorScreen(
    eventId: Long,
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val existingEvent = remember(eventId) { viewModel.getEventById(eventId) }
    var event by remember(eventId) { mutableStateOf(existingEvent?.event ?: "") }

    // Извлекаем время, если событие существует
    val timeParts = remember(existingEvent) {
        if (existingEvent != null) parseTimeRange(existingEvent.time) else listOf("", "", "", "")
    }

    var startHours by remember(eventId) { mutableStateOf(timeParts[0]) }
    var startMinutes by remember(eventId) { mutableStateOf(timeParts[1]) }
    var endHours by remember(eventId) { mutableStateOf(timeParts[2]) }
    var endMinutes by remember(eventId) { mutableStateOf(timeParts[3]) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Заголовок
            Text("Событие", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Название
            OutlinedTextField(
                value = event,
                onValueChange = { event = it },
                label = { Text("Название события") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Блок времени "Начало"
            Text("Начало", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startHours,
                    onValueChange = { startHours = it.take(2) },
                    label = { Text("Часы") },
                    modifier = Modifier.weight(1f)
                )
                Text(":", modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                OutlinedTextField(
                    value = startMinutes,
                    onValueChange = { startMinutes = it.take(2) },
                    label = { Text("Минуты") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Блок времени "Конец"
            Text("Конец", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = endHours,
                    onValueChange = { endHours = it.take(2) },
                    label = { Text("Часы") },
                    modifier = Modifier.weight(1f)
                )
                Text(":", modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                OutlinedTextField(
                    value = endMinutes,
                    onValueChange = { endMinutes = it.take(2) },
                    label = { Text("Минуты") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка внизу карточки
            Button(
                onClick = {
                    val startTime = "${startHours.padStart(2, '0')}:${startMinutes.padStart(2, '0')}"
                    val endTime = "${endHours.padStart(2, '0')}:${endMinutes.padStart(2, '0')}"
                    val fullTime = "$startTime - $endTime" // или сохраняй раздельно
                    if (existingEvent != null) {
                        viewModel.updateEvent(eventId, event, fullTime)
                    } else {
                        viewModel.addEvent(event, fullTime)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (existingEvent != null) "Сохранить изменения" else "Добавить событие")
            }
        }
    }
}