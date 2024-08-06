package com.materip.matetrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.materip.matetrip.navigation.SetUpNavGraph
import com.materip.matetrip.ui.theme.MatetripTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatetripTheme {
                val navHost = rememberNavController()
                SetUpNavGraph(navHost)
            }
        }
    }
}