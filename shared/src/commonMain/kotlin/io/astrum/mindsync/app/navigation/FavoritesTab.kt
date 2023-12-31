package io.astrum.mindsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.astrum.mindsync.app.localization.getCurrentLocalization
import io.astrum.mindsync.app.ui.icon.ApplicationIcons
import io.astrum.mindsync.app.ui.screens.ProTemplateFeature

internal object FavoritesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(ApplicationIcons.Favorite)

            return remember {
                TabOptions(
                    index = 1u,
                    title = getCurrentLocalization().favorites,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        ProTemplateFeature()
    }
}
