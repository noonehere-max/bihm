package com.example.bihm.data

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long,
    val dateAdded: Long = 0L
)
