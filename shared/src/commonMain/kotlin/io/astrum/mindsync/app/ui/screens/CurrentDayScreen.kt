package io.astrum.mindsync.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.astrum.mindsync.app.data.repositories.SimpleRowCalendarDataSource
import io.astrum.mindsync.app.ui.components.DailyNote
import io.astrum.mindsync.app.ui.components.SimpleRowCalendar
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CurrentDay(
    date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    modifier: Modifier = Modifier
) {
//    val localization = getCurrentLocalization()
    val dataSource = SimpleRowCalendarDataSource()
    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = date)) }

    fun onDateClickListener(date: SimpleRowCalendarUiModel.Date) {
        // refresh the CalendarUiModel with new data
        // by changing only the `selectedDate` with the date selected by User
        calendarUiModel = calendarUiModel.copy(
            selectedDate = date,
            visibleDates = calendarUiModel.visibleDates.map {
                it.copy(
                    isSelected = it.date == date.date
                )
            }
        )
    }
    onDateClickListener(calendarUiModel.selectedDate)
    fun onPreviousClickListener(
        startDate: LocalDate
    ) {
        calendarUiModel = dataSource.getData(
            startDate.minus(1, DateTimeUnit.DAY),
            calendarUiModel.selectedDate.date
        )
    }

    fun onNextClickListener(
        startDate: LocalDate
    ) {
        calendarUiModel = dataSource.getData(
            startDate.plus(2, kotlinx.datetime.DateTimeUnit.DAY),
            calendarUiModel.selectedDate.date
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleRowCalendar(calendarUiModel,
            onPrevClickListener = { startDate ->
                onPreviousClickListener(startDate)
            },
            onNextClickListener = { startDate ->
                onNextClickListener(startDate)
            },
            onDateClickListener = { date ->
                onDateClickListener(date)
            }
        )
        DailyNote(calendarUiModel)
        Text(text = "This part is for the 'Created today' row")
    }
}
