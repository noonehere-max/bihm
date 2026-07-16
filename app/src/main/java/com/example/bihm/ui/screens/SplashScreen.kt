package com.example.bihm.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bihm.ui.theme.ElegantFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val LETTER_STAGGER_MS = 150
private const val LETTER_ANIM_MS = 750
private const val LINE_ANIM_MS = 650
private const val HOLD_MS = 800L
private const val FADE_OUT_MS = 450

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val letters = remember { "BIHM".toList() }
    val letterProgress = remember { letters.map { Animatable(0f) } }
    val lineProgress = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        letterProgress.forEachIndexed { index, anim ->
            launch {
                delay((index * LETTER_STAGGER_MS).toLong())
                anim.animateTo(1f, tween(LETTER_ANIM_MS, easing = EaseOutCubic))
            }
        }
        delay((letters.size * LETTER_STAGGER_MS + LETTER_ANIM_MS / 2).toLong())
        lineProgress.animateTo(1f, tween(LINE_ANIM_MS, easing = EaseOutCubic))
        delay(HOLD_MS)
        contentAlpha.animateTo(0f, tween(FADE_OUT_MS))
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .alpha(contentAlpha.value),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                letters.forEachIndexed { index, letter ->
                    val progress = letterProgress[index].value
                    Text(
                        text = letter.toString(),
                        fontFamily = ElegantFontFamily,
                        fontSize = 76.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.graphicsLayer {
                            alpha = progress
                            translationY = (1f - progress) * 20.dp.toPx()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            Box(
                modifier = Modifier
                    .width((140 * lineProgress.value).dp)
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}
