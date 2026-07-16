package com.example.bihm.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bihm.data.Song
import com.example.bihm.ui.theme.ConsoleFontFamily
import com.example.bihm.ui.theme.ElegantFontFamily
import com.example.bihm.ui.utils.formatTime

@Composable
fun PlayerScreen(
    currentSong: Song?,
    isPlaying: Boolean,
    currentPosition: Int,
    showAlbumArt: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DiffuseBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showAlbumArt) {
                GlassAlbumArt()
                Spacer(modifier = Modifier.height(44.dp))
            }

            AnimatedContent(
                targetState = currentSong,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300)) +
                        slideInVertically(animationSpec = tween(300)) { it / 10 })
                        .togetherWith(
                            fadeOut(animationSpec = tween(200)) +
                                slideOutVertically(animationSpec = tween(200)) { -it / 10 }
                        )
                },
                label = "player_song_info"
            ) { song ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = song?.title ?: "Selecciona una canción",
                        fontFamily = ElegantFontFamily,
                        fontSize = 32.sp,
                        lineHeight = 38.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = song?.artist?.uppercase() ?: "",
                        fontFamily = ConsoleFontFamily,
                        fontSize = 11.sp,
                        letterSpacing = 3.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            PlayerSlider(
                currentPosition = currentPosition,
                duration = (currentSong?.duration ?: 0L).toInt(),
                onSeek = onSeek
            )

            Spacer(modifier = Modifier.height(20.dp))

            ControlsRow(
                isPlaying = isPlaying,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun DiffuseBackground() {
    val accent = MaterialTheme.colorScheme.primary
    val gray = MaterialTheme.colorScheme.onSurfaceVariant

    val transition = rememberInfiniteTransition(label = "diffuse_drift")
    val drift1 by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift1"
    )
    val drift2 by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift2"
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight

        DiffuseBlob(
            color = gray.copy(alpha = 0.20f),
            size = 420.dp,
            x = width * (-0.25f + drift1 * 0.15f),
            y = height * (-0.10f + drift2 * 0.08f)
        )
        DiffuseBlob(
            color = accent.copy(alpha = 0.32f),
            size = 380.dp,
            x = width * (0.45f - drift2 * 0.12f),
            y = height * (0.55f + drift1 * 0.10f)
        )
        DiffuseBlob(
            color = accent.copy(alpha = 0.16f),
            size = 300.dp,
            x = width * (0.05f + drift2 * 0.10f),
            y = height * (0.75f - drift1 * 0.08f)
        )
    }
}

@Composable
private fun DiffuseBlob(
    color: Color,
    size: Dp,
    x: Dp,
    y: Dp
) {
    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(size)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(color, Color.Transparent)
                ),
                shape = CircleShape
            )
    )
}

@Composable
private fun GlassAlbumArt() {
    Box(
        modifier = Modifier
            .size(240.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.09f),
                shape = RoundedCornerShape(36.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = null,
            modifier = Modifier.size(52.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerSlider(
    currentPosition: Int,
    duration: Int,
    onSeek: (Int) -> Unit
) {
    val safeDuration = duration.coerceAtLeast(0)
    val safePosition = currentPosition.coerceIn(0, safeDuration)

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = safePosition.toFloat(),
            onValueChange = { onSeek(it.toInt()) },
            valueRange = 0f..safeDuration.toFloat().coerceAtLeast(1f),
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.10f)
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(safePosition),
                fontFamily = ConsoleFontFamily,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = formatTime(safeDuration),
                fontFamily = ConsoleFontFamily,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ControlsRow(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TonalControlButton(
            onClick = onPrevious,
            contentDescription = "Anterior"
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onPlayPause) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                    modifier = Modifier.size(44.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        TonalControlButton(
            onClick = onNext,
            contentDescription = "Siguiente"
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun TonalControlButton(
    onClick: () -> Unit,
    contentDescription: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick, content = content)
    }
}
