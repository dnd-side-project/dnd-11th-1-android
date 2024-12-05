package com.materip.matetrip.splash

import android.content.Intent
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.icon.Logo
import com.materip.matetrip.MainActivity
import com.materip.matetrip.R
import com.materip.matetrip.ui.theme.MatetripTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            MatetripTheme{
                SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen(){
        val alpha = remember{ Animatable(0f) }
        var screenNumber by remember{mutableStateOf(0)}
        LaunchedEffect(Unit) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(750)
            )
            delay(750)
            screenNumber = 1
            delay(1_000)
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(1000)
            )
            Intent(this@SplashActivity, MainActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.let{ intent ->
                startActivity(intent)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ){
            Crossfade(
                targetState = screenNumber,
                label = "Splash Screen"
            ){
                when(it){
                    0 -> {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(Logo.app_icon_60),
                            contentDescription = "Splash Icon 1"
                        )
                    }
                    1 -> {
                        Image(
                            modifier = Modifier.width(width = 200.dp).height(height = 84.dp),
                            painter = painterResource(Logo.splash_icon_01),
                            contentDescription = "Splash Icon 2"
                        )
                    }
                }
            }
        }
    }
}