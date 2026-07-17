# BIHM

A local music player for Android built with Jetpack Compose. Pure minimalism
on the surface, Pixel Experience polish underneath, and a touch of
glassmorphism in the player.

## Features

- Elegant splash animation: the BIHM wordmark appears letter by letter in a
  refined serif typeface.
- Minimalist song library with console-style monospace typography (Space Mono).
- Search by title or artist.
- Sort by title, artist, duration or date added, ascending or descending.
- Full-screen player with a diffuse gradient background (neutral gray blended
  with your accent color) that drifts slowly while you listen.
- Glass-style album art card, tonal controls and an accent-filled play button.
- Title-only mode: hide the album art and keep nothing but the song title.
- System equalizer integration.
- Interface settings: light, dark or system theme, eight accent colors,
  optional album art, all persisted across launches.

## Tech stack

- Kotlin
- Jetpack Compose with Material 3
- Navigation Compose
- MediaPlayer
- SharedPreferences for settings persistence
- Minimum SDK 23, target SDK 36

## Typography

- Italiana - wordmark and player song titles (elegant serif).
- Space Mono - song list, time labels and section headers (console feel).
- Inter - general UI text.

All fonts are bundled under `app/src/main/res/font` and are distributed under
the SIL Open Font License.

## Build

```bash
./gradlew assembleDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

To install on a connected device:

```bash
./gradlew installDebug
```

## Permissions

BIHM only reads audio files stored on the device:

- `READ_MEDIA_AUDIO` on Android 13 and above.
- `READ_EXTERNAL_STORAGE` on older versions.

## Project structure

```
app/src/main/java/com/example/bihm/
├── MainActivity.kt          # Entry point, navigation and permission flow
├── data/                    # Song model and local media scanner
├── ui/
│   ├── navigation/          # Navigation routes
│   ├── player/              # Playback state holder
│   ├── screens/             # Splash, Home, Player and Settings screens
│   ├── settings/            # Persisted settings state
│   ├── theme/               # Colors, typography and theme setup
│   └── utils/               # Time formatting helpers
```

## Version

2.1.0
