package io.astrum.mindsync.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import io.astrum.mindsync.app.ui.screens.viewmodel.SimpleRowCalendarUiModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DailyNoteEditor(calendarUiModel: SimpleRowCalendarUiModel) {
    val state = rememberRichTextState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputModifier = Modifier
        .fillMaxWidth()
    val richTextEditorColors = RichTextEditorDefaults.richTextEditorColors(
        containerColor = MaterialTheme.colorScheme.surface,
        // test to fine the best color
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
    )
    RichTextEditor(
        state = state,
        modifier = inputModifier,
        singleLine = true,
        colors = richTextEditorColors,
        leadingIcon = {
            Row(
                // align the icons to the start without space between them
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Insert Block",
                )
                Icon(
                    imageVector = Icons.Filled.DragIndicator,
                    contentDescription = "Drag Block"
                )
            }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = { }
        )
        Text(
            text = "task number 1"
        )
    }
}
