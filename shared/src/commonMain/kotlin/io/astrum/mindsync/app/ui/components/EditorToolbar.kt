package io.astrum.mindsync.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.KeyboardHide
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import io.astrum.mindsync.app.ui.screens.viewmodel.TextFormatOptionUiModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorToolbar(
    textFormatOptionUiModel: TextFormatOptionUiModel,
    keyboardController: SoftwareKeyboardController?,
    onBoldClick: (TextFormatOptionUiModel) -> Unit,
    onItalicClick: (TextFormatOptionUiModel) -> Unit,
    onUnderlinedClick: (TextFormatOptionUiModel) -> Unit
) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(0.8.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    )
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
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
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Insert Block",
                )
            }
            EditorToolbarButton(
                Icons.Filled.FormatBold,
                "Format Bold",
                textFormatOptionUiModel.isBold
            ){
                onBoldClick(textFormatOptionUiModel)
            }
            EditorToolbarButton(
                Icons.Filled.FormatItalic,
                "Format Italic",
                textFormatOptionUiModel.isItalic
            ) {
                onItalicClick(textFormatOptionUiModel)
            }
            EditorToolbarButton(
                Icons.Filled.FormatUnderlined,
                "Format Underline",
                textFormatOptionUiModel.isUnderlined
            ) {
                onUnderlinedClick(textFormatOptionUiModel)
            }
            // Add other formatting options as needed
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
                    imageVector = Icons.Filled.KeyboardHide,
                    contentDescription = "Hide Keyboard",
                )
            }
        }
    }
}

@Composable
fun EditorToolbarButton(
    imageVector: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}
