package io.astrum.mindsync.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel
import kotlinx.datetime.LocalDate


@Composable
fun SimpleRowCalendar(
    data: SimpleRowCalendarUiModel,
    // calbacks to click previous & back button should be registered outside
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
    onDateClickListener: (SimpleRowCalendarUiModel.Date) -> Unit,
) {
    Row {
        IconButton(onClick = { onPrevClickListener(data.startDate.date) }) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Previous"
            )
        }
        SimpleRowCalendarContent(data, onDateClickListener)
        IconButton(onClick = { onNextClickListener(data.endDate.date) }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(0.8.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
}

@Composable
private fun SimpleRowCalendarContent(
    data: SimpleRowCalendarUiModel,
    onDateClickListener: (SimpleRowCalendarUiModel.Date) -> Unit,
) {
    LazyRow {
        items(items = data.visibleDates) { date ->
            ContentSimpleRowCalendarItem(date, onDateClickListener)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSimpleRowCalendarItem(
    date: SimpleRowCalendarUiModel.Date,
    onDateClickListener: (SimpleRowCalendarUiModel.Date) -> Unit
) {
    val isSelected = date.isSelected
    val modifier: Modifier = Modifier
        .width(40.dp)
        .height(48.dp)
        .padding(1.dp)
        .clickable {
            onDateClickListener(date)
        }
    Column(
        modifier = if (isSelected) {
            modifier.border(
                BorderStroke(0.4.dp, MaterialTheme.colorScheme.primary),
                ShapeDefaults.ExtraSmall
            ).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        } else {
            modifier
        }
    ) {
        Text(
            text = date.date.dayOfMonth.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp,
            fontWeight = if(date.isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (date.isToday) {
                Color.Red
            } else if(date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )
        Text(
            text = date.day.substring(0, 3),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 8.sp,
            fontWeight = if(date.isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Light
            },
            color = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}
