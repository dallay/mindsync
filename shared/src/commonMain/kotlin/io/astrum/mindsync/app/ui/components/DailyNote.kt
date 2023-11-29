package io.astrum.mindsync.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel
import kotlinx.datetime.DayOfWeek

private val dayColorMap = mapOf(
    DayOfWeek.MONDAY to Color(0xFFE91E63),
    DayOfWeek.TUESDAY to Color(0xFF9C27B0),
    DayOfWeek.WEDNESDAY to Color(0xFF673AB7),
    DayOfWeek.THURSDAY to Color(0xFF3F51B5),
    DayOfWeek.FRIDAY to Color(0xFF2196F3),
    DayOfWeek.SATURDAY to Color(0xFF009688),
    DayOfWeek.SUNDAY to Color(0xFF4CAF50),
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyNote(calendarUiModel: SimpleRowCalendarUiModel) {
    val selectedLocalDate = calendarUiModel.selectedDate.date
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = camelCase(selectedLocalDate.dayOfWeek.name),
                color = dayColorMap[selectedLocalDate.dayOfWeek]
                    ?: MaterialTheme.colorScheme.primary
            )

            if (calendarUiModel.selectedDate.isToday) {
                Spacer(modifier = Modifier.width(8.dp))
                Badge(
                    modifier = Modifier.padding(2.dp),
                    containerColor = Color.Red.copy(alpha = 0.2f),
                ) { Text(text = "Today", color = Color.Red, fontWeight = FontWeight.Bold) }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${
                    camelCase(selectedLocalDate.month.name)
                } ${selectedLocalDate.dayOfMonth}, ${selectedLocalDate.year}",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 25.sp,
            )
            Row(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .clickable { println("Page") }) {
                Icon(
                    imageVector = Icons.Filled.OpenInNew,
                    contentDescription = "Page"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Page")
            }
        }
        DailyNoteEditor(calendarUiModel)
    }
}

private fun camelCase(str: String): String = str.lowercase()
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
