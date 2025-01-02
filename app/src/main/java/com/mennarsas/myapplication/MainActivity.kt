package com.mennarsas.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mennarsas.myapplication.navigation.AppNavigation
import com.mennarsas.myapplication.theme.MennarreservasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MennarreservasTheme {
                AppNavigation()
            }
        }
    }
}