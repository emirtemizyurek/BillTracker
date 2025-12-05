package org.example.billtracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.billtracker.ui.DashboardScreen
import org.example.billtracker.ui.MainViewModel
import org.example.billtracker.ui.theme.AppTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val viewModel = koinInject<MainViewModel>()
        val theme by viewModel.theme.collectAsState()

        AppTheme(theme = theme) {
            DashboardScreen()
        }
    }
}
