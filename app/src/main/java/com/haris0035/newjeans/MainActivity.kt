package com.haris0035.newjeans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.haris0035.newjeans.navigation.SetupNavGraph
import com.haris0035.newjeans.ui.theme.NewJeansTheme
import com.haris0035.newjeans.ui.theme.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewJeansTheme {
                SetupNavGraph()
            }
        }
    }
}

