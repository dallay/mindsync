package io.astrum.mindsync.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.astrum.mindsync.app.ui.screens.viewmodel.TextFormatOptionUiModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RichTextEditor() {
    var textState by remember { mutableStateOf("Escribe aquí...") }
    var textFormatOptionUiModel by remember { mutableStateOf(TextFormatOptionUiModel.Default) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        // Text Editor
        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = if (textFormatOptionUiModel.isBold) FontWeight.Bold else null,
                fontStyle = if (textFormatOptionUiModel.isItalic) FontStyle.Italic else null,
                textDecoration = if (textFormatOptionUiModel.isUnderlined) TextDecoration.LineThrough else null
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.White)
                .clickable { }
                .clip(MaterialTheme.shapes.small)
                .shadow(1.dp)
        )

        EditorToolbar(textFormatOptionUiModel, keyboardController,
            onBoldClick = {
                textFormatOptionUiModel = textFormatOptionUiModel.copy(isBold = !it.isBold)
            },
            onItalicClick = {
                textFormatOptionUiModel = textFormatOptionUiModel.copy(isItalic = !it.isItalic)
            },
            onUnderlinedClick = {
                textFormatOptionUiModel =
                    textFormatOptionUiModel.copy(isUnderlined = !it.isUnderlined)
            }
        )
    }
}
