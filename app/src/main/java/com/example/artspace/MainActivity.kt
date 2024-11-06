package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.artspace.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }

    data class Artwork(
        val title: String,
        val artist: String,
        val year: Int,
        @DrawableRes val image: Int
    )

    object Data {
        val images = listOf(
            Artwork(
                title = "Название 1",
                artist = "Автор 1",
                year = 2024,
                image = R.drawable.art1
            ),
            Artwork(
                title = "Название 1",
                artist = "Автор 1",
                year = 2023,
                image = R.drawable.art2
            ),
            Artwork(
                title = "Название 1",
                artist = "Автор 1",
                year = 2022,
                image = R.drawable.art3
            )
        )
    }

    @Composable
    fun ArtSpaceScreen() {
        val artworkList = Data.images
        var currentArtworkIndex by remember { mutableStateOf(0) }
        val currentArtwork = artworkList[currentArtworkIndex]

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = currentArtwork.title, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "by ${currentArtwork.artist}", fontSize = 16.sp)
        }
    }
}
