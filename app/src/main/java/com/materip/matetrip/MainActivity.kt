package com.materip.matetrip

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.materip.feature_login.navigation.LoginRoute
import com.materip.matetrip.navigation.SetUpNavGraph
import com.materip.matetrip.ui.theme.MatetripTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val startDestination = viewModel.getDestination()

            MatetripTheme {
                val navHost = rememberNavController()

                SetUpNavGraph(
                    navController = navHost,
                    startDestination = startDestination
                )
            }
        }
    }
}