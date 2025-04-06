package com.example.ass2.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ass2.Screens.*

enum class AppScreen {
    MAIN,
    URGENT,
    NOT_URGENT
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(AppScreen.MAIN) }
    Surface(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            AppScreen.MAIN -> {
                StudyManagementScreen(
                    onNavigateToUrgent = { currentScreen = AppScreen.URGENT },
                    onNavigateToNotUrgent = { currentScreen = AppScreen.NOT_URGENT }
                )
            }
            AppScreen.URGENT -> {
                UrgentAndImportantScreen(onBackToMain = { currentScreen = AppScreen.MAIN })
            }
            AppScreen.NOT_URGENT -> {
                UrgentNotImportantScreen(onBackToMain = { currentScreen = AppScreen.MAIN })
            }
        }
    }
}
