package io.astrum.mindsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import io.astrum.mindsync.app.localization.getCurrentLocalization
import io.astrum.mindsync.app.ui.icon.ApplicationIcons
import io.astrum.mindsync.app.ui.screens.ProfileTabScreen

internal object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(ApplicationIcons.Person)

            return remember {
                TabOptions(
                    index = 4u,
                    title = getCurrentLocalization().profile,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(ProfileTabScreen()) {
            ScaleTransition(it)
        }
    }
}
