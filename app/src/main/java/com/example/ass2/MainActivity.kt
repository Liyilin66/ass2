package com.example.ass2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat // 添加导入
import com.example.ass2.UITheme.CP3406ASS1Theme
import com.example.ass2.Navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // 替代方案
        setContent {
            CP3406ASS1Theme {
                AppNavigation()
            }
        }
    }
}
