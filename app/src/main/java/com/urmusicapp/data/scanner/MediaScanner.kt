package com.urmusicapp.data.scanner

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.urmusicapp.data.model.AudioFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MediaScanner {
    fun scanAudio(context: Context): Flow<List<AudioFile>> {
        return flow {
            val audioFiles = withContext(Dispatchers.IO) {
                queryAudio(context.contentResolver)
            }
            emit(audioFiles)
        }.buffer().conflate()
    }

    private fun queryAudio(contentResolver: ContentResolver): List<AudioFile> {
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.BIT_DEPTH,
            MediaStore.Audio.Media.SAMPLE_RATE
        )
        val selection = "(${MediaStore.Audio.Media.MIME_TYPE}=? OR ${MediaStore.Audio.Media.MIME_TYPE}=? OR ${MediaStore.Audio.Media.MIME_TYPE}=?)"
        val selectionArgs = arrayOf("audio/mpeg", "audio/flac", "audio/wav")
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val audioFiles = mutableListOf<AudioFile>()
        contentResolver.query(collection, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)
            val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val bitDepthIndex = cursor.getColumnIndex(MediaStore.Audio.Media.BIT_DEPTH)
            val sampleRateIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SAMPLE_RATE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val contentUri = ContentUris.withAppendedId(collection, id)
                val bitDepth = if (bitDepthIndex >= 0) cursor.getInt(bitDepthIndex) else null
                val sampleRate = if (sampleRateIndex >= 0) cursor.getInt(sampleRateIndex) else null

                audioFiles.add(
                    AudioFile(
                        id = id,
                        title = cursor.getString(titleIndex),
                        artist = cursor.getString(artistIndex),
                        album = cursor.getString(albumIndex),
                        contentUri = contentUri.toString(),
                        mimeType = cursor.getString(mimeTypeIndex),
                        durationMs = cursor.getLong(durationIndex),
                        sizeBytes = cursor.getLong(sizeIndex),
                        bitDepth = bitDepth,
                        sampleRate = sampleRate
                    )
                )
            }
        }

        return audioFiles
    }
}
