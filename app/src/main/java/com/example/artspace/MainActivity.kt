package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.R

data class Artwork(
    val title: String,
    val artist: String,
    val year: Int,
    @DrawableRes val image: Int
)

object Data {
    val images = listOf(
        Artwork(
            title = "Дети в санях",
            artist = "Пелевин Иван Андреевич",
            year = 1870,
            image = R.drawable.kids
        ),
        Artwork(
            title = "Иван Шишкин",
            artist = "Утро в сосновом лесу",
            year = 1889,
            image = R.drawable.shishkinn
        ),
        Artwork(
            title = "Воскресный день",
            artist = "Корзухин Алексей",
            year = 1884,
            image = R.drawable.sunday
        )
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    val artworkList = Data.images
    var currentArtworkIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworkList[currentArtworkIndex]

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            ArtworkWall(
                image = currentArtwork.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ArtworkDescriptor(
                title = currentArtwork.title,
                artist = currentArtwork.artist,
                year = currentArtwork.year,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        DisplayController(
            onPreviousArtwork = {
                currentArtworkIndex = if (currentArtworkIndex > 0) {
                    currentArtworkIndex - 1
                } else {
                    artworkList.size - 1
                }
            },
            onNextArtwork = {
                currentArtworkIndex = if (currentArtworkIndex < artworkList.size - 1) {
                    currentArtworkIndex + 1
                } else {
                    0
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun ArtworkWall(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val height = if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) 200.dp else 300.dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(elevation = 13.dp)
            .background(Color.White)
            .padding(20.dp)
            .height(height)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null
        )
    }
}

@Composable
fun ArtworkDescriptor(
    title: String,
    artist: String,
    year: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .wrapContentHeight()
            .background(Color(0xFFecebf4))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Light,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(artist)
                }
                append(" ($year)")
            },
            fontSize = 18.sp
        )
    }
}

@Composable
fun DisplayController(
    onPreviousArtwork: () -> Unit,
    onNextArtwork: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onPreviousArtwork,
            modifier = Modifier
                .width(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Назад")
        }
        Button(
            onClick = onNextArtwork,
            modifier = Modifier
                .width(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Дальше")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}