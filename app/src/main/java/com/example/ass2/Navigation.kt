package com.example.ass2.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ass2.Screens.*

enum class AppScreen {
    LOGIN, // 新增登录页面
    SIGN_UP, // 新增注册页面
    MAIN,
    URGENT,
    NOT_URGENT,
    IMPORTANT,
    STUDY_REVIEW
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(AppScreen.LOGIN) } // 默认启动登录页面
    Surface(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            AppScreen.LOGIN -> {
                LoginScreen(
                    onNavigateToSignUp = { currentScreen = AppScreen.SIGN_UP },
                    onLoginSuccess = { currentScreen = AppScreen.MAIN }
                )
            }
            AppScreen.SIGN_UP -> {
                SignUpScreen(
                    onBackToLogin = { currentScreen = AppScreen.LOGIN }
                )
            }
            AppScreen.MAIN -> {
                StudyManagementScreen(
                    onNavigateToUrgent = { currentScreen = AppScreen.URGENT },
                    onNavigateToNotUrgent = { currentScreen = AppScreen.NOT_URGENT },
                    onNavigateToImportant = { currentScreen = AppScreen.IMPORTANT },
                    onNavigateToStudyAndReview = { currentScreen = AppScreen.STUDY_REVIEW }
                )
            }
            AppScreen.URGENT -> {
                UrgentAndImportantScreen(onBackToMain = { currentScreen = AppScreen.MAIN })
            }
            AppScreen.NOT_URGENT -> {
                UrgentNotImportantScreen(onBackToMain = { currentScreen = AppScreen.MAIN })
            }
            AppScreen.IMPORTANT -> {
                ImportantNotUrgentScreen(onBackToMain = { currentScreen = AppScreen.MAIN })
            }
            AppScreen.STUDY_REVIEW -> {
                StudyAndReviewContent(onBack = { currentScreen = AppScreen.MAIN })
            }
        }
    }
}

