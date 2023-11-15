package io.astrum.mindsync.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.ScaleTransition
import io.astrum.mindsync.app.feature.petupload.PetUploadScreen
import io.astrum.mindsync.app.localization.getCurrentLocalization
import io.astrum.mindsync.app.ui.icon.ApplicationIcons

internal object PetUploadTab : Tab {
    private val localization = getCurrentLocalization()

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(ApplicationIcons.Create)

            return remember {
                TabOptions(
                    index = 2u,
                    title = localization.upload,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(PetUploadScreen()) {
            ScaleTransition(it)
        }
    }
}
