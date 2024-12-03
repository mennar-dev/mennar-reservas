package com.mennarsas.myapplication.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mennarsas.myapplication.R
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import com.mennarsas.myapplication.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val circleSize1 = remember { Animatable(0f) }
    val circleSize2 = remember { Animatable(0f) }
    val circleSize3 = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        launch {
            circleSize1.animateTo(
                targetValue = 300f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(1000, easing = FastOutSlowInEasing)
                )
            )
        }
        launch {
            delay(500)
            circleSize2.animateTo(
                targetValue = 200f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(1000, easing = FastOutSlowInEasing)
                )
            )
        }
        launch {
            delay(250)
            circleSize3.animateTo(
                targetValue = 250f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(1000, easing = FastOutSlowInEasing)
                )
            )
        }
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            )
        }

        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = PrimaryColor.copy(alpha = 0.1f),
                radius = circleSize1.value,
                center = Offset(size.width * 0.2f, size.height * 0.3f)
            )
            drawCircle(
                color = PrimaryColor.copy(alpha = 0.1f),
                radius = circleSize2.value,
                center = Offset(size.width * 0.8f, size.height * 0.7f)
            )
            drawCircle(
                color = PrimaryColor.copy(alpha = 0.1f),
                radius = circleSize3.value,
                center = Offset(size.width * 0.5f, size.height * 0.5f)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(logoScale.value)
        )
    }
}