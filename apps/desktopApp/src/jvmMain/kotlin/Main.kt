
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.astrum.mindsync.app.MainApp
import io.astrum.mindsync.app.di.commonModule
import io.astrum.mindsync.app.ui.theme.MultiplatformKickstarterTheme
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(commonModule)
    }
    Window(title = "MindSync", onCloseRequest = ::exitApplication) {
        MultiplatformKickstarterTheme {
            MainApp()
        }
    }
}
