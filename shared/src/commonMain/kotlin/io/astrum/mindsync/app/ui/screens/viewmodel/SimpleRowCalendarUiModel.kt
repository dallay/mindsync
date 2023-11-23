package io.astrum.mindsync.app.ui.screens.viewmodel

import kotlinx.datetime.LocalDate

data class SimpleRowCalendarUiModel(
    val selectedDate: Date, // the date selected by the User. by default is Today.
    val visibleDates: List<Date> // the dates shown on the screen
) {

    val startDate: Date = visibleDates.first() // the first of the visible dates
    val endDate: Date = visibleDates.last() // the last of the visible dates

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.dayOfWeek.name // get the day by accessing the dayOfWeek property
    }
}
