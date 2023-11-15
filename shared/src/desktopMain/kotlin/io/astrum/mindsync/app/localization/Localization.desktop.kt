package io.astrum.mindsync.app.localization

import androidx.compose.runtime.Composable
import io.astrum.mindsync.app.localization.AvailableLanguages

actual fun getCurrentLanguage(): AvailableLanguages = AvailableLanguages.EN

@Composable
actual fun SetLanguage(language: AvailableLanguages) {
}
