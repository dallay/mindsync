package io.astrum.mindsync.app.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    private val toolbarButtons = listOf(
        ToolbarButton(id = "ai_block",
            icon = ApplicationIcons.Sparkle,
            contentDescription = "Insert Block",
            onClick = {}),
        ToolbarButton(id = "insert_block",
            icon = ApplicationIcons.Add,
            contentDescription = "Insert Block",
            onClick = {}),
        ToolbarButton(id = "transform_block",
            icon = ApplicationIcons.Repeat,
            contentDescription = "Transform a Block to another",
            onClick = {}),
        ToolbarButton(id = "image_block",
            icon = ApplicationIcons.PhotoLibrary,
            contentDescription = "Insert Image",
            onClick = {}),
        ToolbarButton(id = "format_text",
            icon = ApplicationIcons.TextFormat,
            contentDescription = "Format Text",
            onClick = {}),
        ToolbarButton(id = "undo",
            icon = ApplicationIcons.Undo,
            contentDescription = "Undo",
            onClick = {}),
        ToolbarButton(id = "mention_someone",
            icon = ApplicationIcons.AlternateEmail,
            contentDescription = "Mention someone",
            onClick = {}),
        ToolbarButton(id = "delete_block",
            icon = ApplicationIcons.Delete,
            contentDescription = "Delete Block",
            onClick = {}),
        ToolbarButton(id = "increase_indentation",
            icon = ApplicationIcons.FormatIndentIncrease,
            contentDescription = "Increase Indentation",
            onClick = {}),
        ToolbarButton(id = "decrease_indentation",
            icon = ApplicationIcons.FormatIndentDecrease,
            contentDescription = "Decrease Indentation",
            onClick = {}),
        ToolbarButton(id = "align_bottom",
            icon = ApplicationIcons.VerticalAlignBottom,
            contentDescription = "Align Bottom",
            onClick = {}),
        ToolbarButton(id = "align_top",
            icon = ApplicationIcons.VerticalAlignTop,
            contentDescription = "Align Top",
            onClick = {}),
        ToolbarButton(id = "more_options",
            icon = ApplicationIcons.MoreHoriz,
            contentDescription = "More Options",
            onClick = {}),
    )

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
            calendarUiModel = calendarUiModel.copy(selectedDate = date,
                visibleDates = calendarUiModel.visibleDates.map {
                    it.copy(
                        isSelected = it.date == date.date
                    )
                })
        }
        onDateClickListener(calendarUiModel.selectedDate)
        fun onPreviousClickListener(
            startDate: LocalDate
        ) {
            calendarUiModel = dataSource.getData(
                startDate.minus(1, DateTimeUnit.DAY), calendarUiModel.selectedDate.date
            )
        }

        fun onNextClickListener(
            startDate: LocalDate
        ) {
            calendarUiModel = dataSource.getData(
                startDate.plus(2, DateTimeUnit.DAY),
                calendarUiModel.selectedDate.date
            )
        }

        ApplicationTheme {
            Scaffold(topBar = {
                SimpleRowCalendar(calendarUiModel, onPrevClickListener = { startDate ->
                    onPreviousClickListener(startDate)
                }, onNextClickListener = { startDate ->
                    onNextClickListener(startDate)
                }, onDateClickListener = { date ->
                    onDateClickListener(date)
                })
            }, bottomBar = {
                DailyNoteEditorToolbar()
            }) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier.padding(it).padding(bottom = 46.dp)
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
        keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    ) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime).imePadding().fillMaxWidth()
        ) {
            val dividerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            Divider(
                color = dividerColor,
                modifier = Modifier.height(0.8.dp).fillMaxHeight().fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                LazyRow(modifier = Modifier.weight(1F)) {
                    items(toolbarButtons) { button ->
                        IconButton(
                            onClick = button.onClick
                        ) {
                            Icon(
                                imageVector = button.icon,
                                contentDescription = button.contentDescription,
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.drawBehind {
                        val strokeWidth = 1 * density
                        val topSpace = 8.dp.toPx()
                        val bottomSpace = 8.dp.toPx()
                        // Draw line function for left border with space at top and bottom
                        drawLine(
                            dividerColor,
                            start = Offset(0f, topSpace),
                            end = Offset(0f, size.height - bottomSpace),
                            strokeWidth
                        )
                    }
                ) {
                    IconButton(onClick = {
                        keyboardController?.hide()
                    }) {
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

data class ToolbarButton(
    val id: String, val icon: ImageVector, val contentDescription: String, val onClick: () -> Unit
)
