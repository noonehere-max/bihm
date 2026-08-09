package com.example.bihm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bihm.ui.navigation.Screen
import com.example.bihm.ui.player.PlayerState
import com.example.bihm.ui.screens.HomeScreen
import com.example.bihm.ui.screens.PlayerScreen
import com.example.bihm.ui.screens.SettingsScreen
import com.example.bihm.ui.screens.SplashScreen
import com.example.bihm.ui.settings.SettingsState
import com.example.bihm.ui.theme.BihmTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onPermissionGranted?.invoke()
    }

    private var onPermissionGranted: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val settingsState = remember { SettingsState(context) }
            val playerState = remember { PlayerState(context) }

            BihmTheme(
                themeMode = settingsState.themeMode,
                accentColor = settingsState.accentColor
            ) {
                LaunchedEffect(Unit) {
                    onPermissionGranted = { playerState.loadSongs() }
                    if (hasAudioPermission()) {
                        playerState.loadSongs()
                    } else {
                        requestAudioPermission()
                    }
                }

                LaunchedEffect(playerState.isPlaying) {
                    while (playerState.isPlaying) {
                        delay(500)
                        playerState.updatePosition()
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        playerState.release()
                        onPermissionGranted = null
                    }
                }

                BihmApp(
                    playerState = playerState,
                    settingsState = settingsState
                )
            }
        }
    }

    private fun hasAudioPermission(): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestAudioPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        permissionLauncher.launch(permission)
    }
}

@Composable
private fun BihmApp(
    playerState: PlayerState,
    settingsState: SettingsState
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(animationSpec = tween(350)) },
        exitTransition = { fadeOut(animationSpec = tween(350)) },
        popEnterTransition = { fadeIn(animationSpec = tween(350)) },
        popExitTransition = { fadeOut(animationSpec = tween(350)) }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen(
                songs = playerState.songs,
                currentSong = playerState.currentSong,
                isPlaying = playerState.isPlaying,
                settingsState = settingsState,
                onSongSelected = { song ->
                    playerState.play(song)
                },
                onPlayPause = { playerState.togglePlayPause() },
                onNext = { playerState.playNext() },
                onPrevious = { playerState.playPrevious() },
                onNavigateToPlayer = { navController.navigate(Screen.Player.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Player.route) {
            PlayerScreen(
                currentSong = playerState.currentSong,
                isPlaying = playerState.isPlaying,
                isShuffle = playerState.isShuffle,
                repeatMode = playerState.repeatMode,
                currentPosition = playerState.currentPosition,
                showAlbumArt = settingsState.showAlbumArt,
                onPlayPause = { playerState.togglePlayPause() },
                onNext = { playerState.playNext() },
                onPrevious = { playerState.playPrevious() },
                onSeek = { playerState.seekTo(it) },
                onToggleShuffle = { playerState.toggleShuffle() },
                onCycleRepeatMode = { playerState.cycleRepeatMode() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                settingsState = settingsState,
                audioSessionId = playerState.audioSessionId,
                onRescanLibrary = { playerState.loadSongs() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
