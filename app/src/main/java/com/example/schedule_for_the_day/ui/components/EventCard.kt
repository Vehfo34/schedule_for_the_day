package com.example.schedule_for_the_day.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventCard(event: String, time: String, modifier: Modifier = Modifier) {
    var color by remember { mutableStateOf(Color (0xFFD55252)) }
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ){
        var isSwitchOn by remember { mutableStateOf(false) }
        var textSwitch by remember { mutableStateOf("") }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(text = event, style = MaterialTheme.typography.titleMedium, fontSize = 20.sp)
                Text(text = time, style = MaterialTheme.typography.bodySmall, fontSize = 16.sp)
            }
            Switch(isSwitchOn, {
                isSwitchOn = it
                if (isSwitchOn) {
                    color = Color(0xFF1AEA59)
                } else {
                    color = Color(0xFFD55252)
                }
            })
            Text(textSwitch)
        }
    }

}


