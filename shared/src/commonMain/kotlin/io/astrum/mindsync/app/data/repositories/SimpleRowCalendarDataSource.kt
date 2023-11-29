package io.astrum.mindsync.app.data.repositories

import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

class SimpleRowCalendarDataSource {
    private val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    fun getData(
        startDate: LocalDate = today,
        lastSelectedDate: LocalDate
    ): SimpleRowCalendarUiModel {
        val visibleDates = getVisibleDates(startDate)
        val selectedDate = getSelectedDate(visibleDates, lastSelectedDate)
        return SimpleRowCalendarUiModel(selectedDate, visibleDates)
    }

    private fun getSelectedDate(
        visibleDates: List<SimpleRowCalendarUiModel.Date>,
        lastSelectedDate: LocalDate
    ): SimpleRowCalendarUiModel.Date {
        /* if the lastSelectedDate is not in the visibleDates,
        then return the same position as the lastSelectedDate was before */
        val index = visibleDates.indexOfFirst { it.date == lastSelectedDate }
        val selectedDate = if (index == -1) {
            // the day that has the same day of the week as the lastSelectedDate
            val dayOfWeek = lastSelectedDate.dayOfWeek
            visibleDates.firstOrNull { it.date.dayOfWeek == dayOfWeek } ?: visibleDates.first()
        } else {
            visibleDates[index]
        }
        return selectedDate.copy(isSelected = true)
    }

    private fun getVisibleDates(startDate: LocalDate): List<SimpleRowCalendarUiModel.Date> {
        val dates = mutableListOf<SimpleRowCalendarUiModel.Date>()
        // Get the current week day
        val currentDayOfWeek: DayOfWeek = startDate.dayOfWeek
        // Get the first day of the week
        val firstDayOfWeek: LocalDate = startDate.minus(currentDayOfWeek.ordinal, DAY)
        // fill the dates with the days of the week
        for (i in 0..<DayOfWeek.values().size) {
            val date = firstDayOfWeek.plus(i, DAY)
            dates.add(
                SimpleRowCalendarUiModel.Date(
                    date,
                    isSelected = false,
                    isToday = date == today
                )
            )
        }
        return dates
    }
}
