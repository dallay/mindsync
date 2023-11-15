package io.astrum.mindsync.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import io.astrum.mindsync.app.ui.screens.MainScreen

@Composable
fun MainApp() {
    Navigator(MainScreen()) {
        ScaleTransition(it)
    }
}
