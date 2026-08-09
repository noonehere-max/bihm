package com.example.bihm.ui.player

import android.content.ContentUris
import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.bihm.data.Song
import com.example.bihm.data.scanLocalMusic
import kotlin.random.Random

enum class RepeatMode {
    NONE, ALL, ONE
}

class PlayerState(private val context: Context) {
    var songs by mutableStateOf<List<Song>>(emptyList())
        private set
    var currentSong by mutableStateOf<Song?>(null)
        private set
    var isPlaying by mutableStateOf(false)
        private set
    var currentPosition by mutableIntStateOf(0)
        private set
    var audioSessionId by mutableIntStateOf(0)
        private set
    var isShuffle by mutableStateOf(false)
        private set
    var repeatMode by mutableStateOf(RepeatMode.NONE)
        private set

    private var mediaPlayer: MediaPlayer? = null

    fun loadSongs() {
        songs = scanLocalMusic(context)
    }

    fun play(song: Song) {
        release()
        val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(context, uri)
                prepare()
                start()
                setOnCompletionListener {
                    if (repeatMode == RepeatMode.ONE) {
                        seekTo(0)
                        start()
                    } else {
                        playNext()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        currentSong = song
        isPlaying = true
        currentPosition = 0
        audioSessionId = mediaPlayer?.audioSessionId ?: 0
    }

    fun togglePlayPause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                isPlaying = false
            } else {
                player.start()
                isPlaying = true
            }
        }
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
    }

    fun cycleRepeatMode() {
        repeatMode = RepeatMode.entries[(repeatMode.ordinal + 1) % RepeatMode.entries.size]
    }

    fun playNext() {
        if (songs.isEmpty()) return
        val index = songs.indexOfFirst { it.id == currentSong?.id }
        if (index == -1) {
            play(songs[0])
            return
        }
        val nextIndex = if (isShuffle) {
            if (songs.size == 1) 0 else {
                var randomIndex: Int
                do {
                    randomIndex = Random.nextInt(songs.size)
                } while (randomIndex == index)
                randomIndex
            }
        } else {
            (index + 1) % songs.size
        }
        play(songs[nextIndex])
    }

    fun playPrevious() {
        if (songs.isEmpty()) return
        val index = songs.indexOfFirst { it.id == currentSong?.id }
        if (index == -1) {
            play(songs[0])
            return
        }
        val prevIndex = if (index <= 0) songs.size - 1 else index - 1
        play(songs[prevIndex])
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position.coerceAtLeast(0))
        currentPosition = position.coerceAtLeast(0)
    }

    fun updatePosition() {
        mediaPlayer?.let { currentPosition = it.currentPosition }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        audioSessionId = 0
    }
}
