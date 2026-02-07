package com.urmusic.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UrMusicApp()
        }
    }
}

@Composable
private fun UrMusicApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Greeting(message = "UrMusic hazır")
        }
    }
}

@Composable
private fun Greeting(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    UrMusicApp()
}
