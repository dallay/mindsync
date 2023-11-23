package io.astrum.mindsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.astrum.mindsync.app.ui.icon.ApplicationIcons
import io.astrum.mindsync.app.ui.screens.CurrentDay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal object CurrentDayTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(ApplicationIcons.Calendar)

            return remember {
                TabOptions(
                    index = 1u,
                    title = getCurrentDayTitle(),
                    icon = icon
                )
            }
        }
    private val localDateTime: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private fun getCurrentDayTitle(): String =
        localDateTime.dayOfMonth.toString()

    @Composable
    override fun Content() {
        CurrentDay(localDateTime.date)
    }
}
