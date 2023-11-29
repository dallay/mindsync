package io.astrum.mindsync.app.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.astrum.mindsync.app.data.repositories.SimpleRowCalendarDataSource
import io.astrum.mindsync.app.ui.components.DailyNote
import io.astrum.mindsync.app.ui.components.SimpleRowCalendar
import io.astrum.mindsync.app.ui.icon.ApplicationIcons
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel
import io.astrum.mindsync.app.ui.theme.ApplicationTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

class CurrentDayScreen(
    private val date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
) : Screen {

    @Composable
    override fun Content() {
        ApplicationTheme {
            CurrentDayView(date)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    fun CurrentDayView(
        date: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    ) {
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

        ApplicationTheme {
            Scaffold(
                topBar = {
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
                },
                bottomBar = {
                    DailyNoteEditorToolbar()
                }
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .padding(it).padding(bottom = 46.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .verticalScroll(scrollState)
                ) {
                    DailyNote(calendarUiModel)
                    Text(text = "This part is for the 'Created today' row")

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Hello World! -> ${
                                WindowInsets.Companion.ime.getBottom(
                                    LocalDensity.current
                                )
                            }"
                        )
                        Text(text = "Not Visible")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
    @Composable
    private fun DailyNoteEditorToolbar(
        keyboardController: SoftwareKeyboardController?
        = LocalSoftwareKeyboardController.current
    ) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime)
                .imePadding()
                .fillMaxWidth()
        ) {
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .height(0.8.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .height(38.dp)
                    .padding(1.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.Add,
                            contentDescription = "Insert Block",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.Repeat,
                            contentDescription = "Transform a Block to another",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.PhotoLibrary,
                            contentDescription = "Insert Image",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.TextFormat,
                            contentDescription = "Format Text",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.Undo,
                            contentDescription = "Undo",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.AlternateEmail,
                            contentDescription = "Mention someone",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.Delete,
                            contentDescription = "Delete Block",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.FormatIndentIncrease,
                            contentDescription = "Increase Indentation",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.FormatIndentDecrease,
                            contentDescription = "Decrease Indentation",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.FormatIndentDecrease,
                            contentDescription = "Decrease Indentation",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.VerticalAlignBottom,
                            contentDescription = "Align Bottom",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.VerticalAlignTop,
                            contentDescription = "Align Top",
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.MoreHoriz,
                            contentDescription = "More Options",
                        )
                    }
                }
                Row {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(0.5.dp).padding(vertical = 5.dp)
                    )
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = ApplicationIcons.KeyboardHide,
                            contentDescription = "Hide Keyboard",
                        )
                    }
                }
            }
        }
    }
}
