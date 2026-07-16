package com.example.bihm.ui.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.bihm.ui.theme.AccentColor
import com.example.bihm.ui.theme.ThemeMode

enum class SortBy {
    TITLE, ARTIST, DURATION, DATE_ADDED
}

private const val PREFS_NAME = "bihm_settings"
private const val KEY_THEME = "theme_mode"
private const val KEY_ACCENT = "accent_color"
private const val KEY_SORT_BY = "sort_by"
private const val KEY_SORT_ASCENDING = "sort_ascending"
private const val KEY_SHOW_ALBUM_ART = "show_album_art"

class SettingsState(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var themeMode by mutableStateOf(ThemeMode.SYSTEM)
        private set
    var accentColor by mutableStateOf(AccentColor.PIXEL_BLUE)
        private set
    var sortBy by mutableStateOf(SortBy.TITLE)
        private set
    var sortAscending by mutableStateOf(true)
        private set
    var showAlbumArt by mutableStateOf(true)
        private set

    init {
        load()
    }

    private fun load() {
        themeMode = ThemeMode.entries.find {
            it.name == prefs.getString(KEY_THEME, ThemeMode.SYSTEM.name)
        } ?: ThemeMode.SYSTEM

        accentColor = AccentColor.entries.find {
            it.name == prefs.getString(KEY_ACCENT, AccentColor.PIXEL_BLUE.name)
        } ?: AccentColor.PIXEL_BLUE

        sortBy = SortBy.entries.find {
            it.name == prefs.getString(KEY_SORT_BY, SortBy.TITLE.name)
        } ?: SortBy.TITLE

        sortAscending = prefs.getBoolean(KEY_SORT_ASCENDING, true)

        showAlbumArt = prefs.getBoolean(KEY_SHOW_ALBUM_ART, true)
    }

    fun updateThemeMode(mode: ThemeMode) {
        themeMode = mode
        prefs.edit().putString(KEY_THEME, mode.name).apply()
    }

    fun updateAccentColor(color: AccentColor) {
        accentColor = color
        prefs.edit().putString(KEY_ACCENT, color.name).apply()
    }

    fun updateSortBy(by: SortBy) {
        sortBy = by
        prefs.edit().putString(KEY_SORT_BY, by.name).apply()
    }

    fun updateSortAscending(ascending: Boolean) {
        sortAscending = ascending
        prefs.edit().putBoolean(KEY_SORT_ASCENDING, ascending).apply()
    }

    fun updateShowAlbumArt(show: Boolean) {
        showAlbumArt = show
        prefs.edit().putBoolean(KEY_SHOW_ALBUM_ART, show).apply()
    }
}

@Composable
fun rememberSettingsState(context: Context): SettingsState {
    return remember { SettingsState(context) }
}
