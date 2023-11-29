package io.astrum.mindsync.app.ui.screens.viewmodel

data class TextFormatOptionUiModel(
    val isBold: Boolean,
    val isItalic: Boolean,
    val isUnderlined: Boolean,
    val isStrikethrough: Boolean,
    val isBullet: Boolean,
    val isNumbered: Boolean,
    val isQuote: Boolean,
    val isLink: Boolean,
    val isCode: Boolean,
    val isClear: Boolean
) {
    companion object {
        val Default = TextFormatOptionUiModel(
            isBold = false,
            isItalic = false,
            isUnderlined = false,
            isStrikethrough = false,
            isBullet = false,
            isNumbered = false,
            isQuote = false,
            isLink = false,
            isCode = false,
            isClear = false
        )
    }
}
