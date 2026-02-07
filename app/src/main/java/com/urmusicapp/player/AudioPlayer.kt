package com.urmusicapp.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.AudioCapabilities
import androidx.media3.exoplayer.audio.DefaultAudioSink

class AudioPlayer(private val context: Context) {
    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(androidx.media3.common.C.USAGE_MEDIA)
        .setContentType(androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC)
        .build()

    private val audioSink = DefaultAudioSink.Builder()
        .setAudioCapabilities(AudioCapabilities.getCapabilities(context))
        .setEnableFloatOutput(true)
        .setEnableAudioTrackPlaybackParams(true)
        .build()

    private val player: ExoPlayer = ExoPlayer.Builder(context)
        .setAudioSink(audioSink)
        .setAudioAttributes(audioAttributes, true)
        .build()

    fun play(uri: Uri, mimeType: String) {
        val mediaItem = buildMediaItem(uri, mimeType)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun buildMediaItem(uri: Uri, mimeType: String): MediaItem {
        val normalizedMimeType = when (mimeType) {
            MimeTypes.AUDIO_FLAC,
            MimeTypes.AUDIO_WAV,
            MimeTypes.AUDIO_MPEG -> mimeType
            else -> MimeTypes.AUDIO_MPEG
        }

        return MediaItem.Builder()
            .setUri(uri)
            .setMimeType(normalizedMimeType)
            .build()
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }
}
