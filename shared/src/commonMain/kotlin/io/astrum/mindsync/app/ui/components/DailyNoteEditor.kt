package io.astrum.mindsync.app.ui.components

import androidx.compose.runtime.Composable
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel

@Composable
fun DailyNoteEditor(calendarUiModel: SimpleRowCalendarUiModel) {
//    Text(
//        text = """
//        day: ${calendarUiModel.selectedDate.day}
//        isToday: ${calendarUiModel.selectedDate.isToday}
//        isSelected: ${calendarUiModel.selectedDate.isSelected}
//    """.trimIndent()
//    )
    RichTextEditor()
}
