package io.astrum.mindsync.app.localization

import androidx.compose.runtime.Composable

actual fun getCurrentLanguage(): AvailableLanguages = AvailableLanguages.EN

@Composable
actual fun SetLanguage(language: AvailableLanguages) {
}
