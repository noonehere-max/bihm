package com.example.bihm.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bihm.ui.settings.SettingsState
import com.example.bihm.ui.settings.SortBy
import com.example.bihm.ui.theme.AccentColor
import com.example.bihm.ui.theme.ConsoleFontFamily
import com.example.bihm.ui.theme.ElegantFontFamily
import com.example.bihm.ui.theme.ThemeMode
import com.example.bihm.ui.theme.paletteFor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    audioSessionId: Int = 0,
    appVersion: String = "2.1.0",
    onRescanLibrary: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ajustes",
                        fontFamily = ElegantFontFamily,
                        fontSize = 26.sp,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SettingsSection(title = "Interfaz") {
                SettingsLabel("Tema")
                ThemeSelector(
                    selected = settingsState.themeMode,
                    onSelect = { settingsState.updateThemeMode(it) }
                )

                SectionDivider()

                SettingsLabel("Color de acento")
                Spacer(modifier = Modifier.height(10.dp))
                AccentColorSelector(
                    selected = settingsState.accentColor,
                    onSelect = { settingsState.updateAccentColor(it) }
                )

                SectionDivider()

                SettingsSwitchRow(
                    title = "Mostrar miniatura de la canción",
                    subtitle = "Si se desactiva, solo se muestra el título",
                    checked = settingsState.showAlbumArt,
                    onCheckedChange = { settingsState.updateShowAlbumArt(it) }
                )
            }

            SettingsSection(title = "Biblioteca") {
                SettingsLabel("Ordenar por")
                Spacer(modifier = Modifier.height(4.dp))
                SortBySelector(
                    selected = settingsState.sortBy,
                    onSelect = { settingsState.updateSortBy(it) }
                )

                SectionDivider()

                SettingsSwitchRow(
                    title = "Orden ascendente",
                    subtitle = null,
                    checked = settingsState.sortAscending,
                    onCheckedChange = { settingsState.updateSortAscending(it) }
                )

                SectionDivider()

                SettingsTextRow(
                    title = "Volver a escanear música",
                    subtitle = "Actualizar la biblioteca local",
                    onClick = onRescanLibrary
                )
            }

            SettingsSection(title = "Audio") {
                SettingsTextRow(
                    title = "Ecualizador",
                    subtitle = "Abrir panel de audio del sistema",
                    onClick = { openSystemEqualizer(context, audioSessionId) }
                )
            }

            SettingsSection(title = "Acerca de") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Versión",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = appVersion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "BIHM es un reproductor de música local de diseño minimalista.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = title.uppercase(),
            fontFamily = ConsoleFontFamily,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(14.dp))
        content()
    }
}

@Composable
private fun SettingsLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun SectionDivider() {
    Spacer(modifier = Modifier.height(14.dp))
    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline)
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    subtitle: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsTextRow(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ThemeSelector(
    selected: ThemeMode,
    onSelect: (ThemeMode) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        ThemeMode.entries.forEach { mode ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .selectable(
                        selected = (mode == selected),
                        onClick = { onSelect(mode) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (mode == selected),
                    onClick = null
                )
                Text(
                    text = when (mode) {
                        ThemeMode.SYSTEM -> "Seguir sistema"
                        ThemeMode.LIGHT -> "Claro"
                        ThemeMode.DARK -> "Oscuro"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun AccentColorSelector(
    selected: AccentColor,
    onSelect: (AccentColor) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        AccentColor.entries.toList().chunked(4).forEach { rowColors ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                rowColors.forEach { accent ->
                    val palette = paletteFor(accent)
                    val color = palette.lightPrimary
                    val isSelected = accent == selected

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { onSelect(accent) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = androidx.compose.ui.graphics.Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SortBySelector(
    selected: SortBy,
    onSelect: (SortBy) -> Unit
) {
    Column {
        SortBy.entries.forEach { sort ->
            val isSelected = sort == selected
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = { onSelect(sort) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = null
                )
                Text(
                    text = when (sort) {
                        SortBy.TITLE -> "Título"
                        SortBy.ARTIST -> "Artista"
                        SortBy.DURATION -> "Duración"
                        SortBy.DATE_ADDED -> "Fecha"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

private fun openSystemEqualizer(context: Context, audioSessionId: Int) {
    val intent = Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL).apply {
        putExtra(AudioEffect.EXTRA_AUDIO_SESSION, audioSessionId)
        putExtra(AudioEffect.EXTRA_PACKAGE_NAME, context.packageName)
        putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
    }
    try {
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast
                .makeText(context, "No hay ecualizador disponible en este dispositivo", Toast.LENGTH_SHORT)
                .show()
        }
    } catch (e: ActivityNotFoundException) {
        Toast
            .makeText(context, "No se pudo abrir el ecualizador", Toast.LENGTH_SHORT)
            .show()
    }
}
