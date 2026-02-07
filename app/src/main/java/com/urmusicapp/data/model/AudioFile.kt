package com.urmusicapp.data.model

data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String?,
    val album: String?,
    val contentUri: String,
    val mimeType: String?,
    val durationMs: Long,
    val sizeBytes: Long,
    val bitDepth: Int?,
    val sampleRate: Int?
)
