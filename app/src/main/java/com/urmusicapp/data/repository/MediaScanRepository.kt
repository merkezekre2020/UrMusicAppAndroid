package com.urmusicapp.data.repository

import android.content.Context
import com.urmusicapp.data.model.AudioFile
import com.urmusicapp.data.scanner.MediaScanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MediaScanRepository(
    context: Context,
    scope: CoroutineScope,
    mediaScanner: MediaScanner = MediaScanner()
) {
    val audioFiles: StateFlow<List<AudioFile>> = mediaScanner
        .scanAudio(context)
        .stateIn(scope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000), emptyList())
}
