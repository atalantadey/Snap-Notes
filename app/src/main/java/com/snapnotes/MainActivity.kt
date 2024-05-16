package com.snapnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.snapnotes.presentation.dashboard.DashboardScreen
import com.snapnotes.presentation.theme.SnapNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapNotesTheme {
                DashboardScreen()
            }
        }
    }
}

