package com.example.artspace

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    DisplayContent()
                }
            }
        }
    }
}

@Composable
fun DisplayContent(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(0) } // Inicialmente, no se muestra ninguna obra de arte

    // Lista de obras de arte
    val artworks = listOf(
        Artwork("La Monalisa", "Leonardo da Vinci", "1503", R.drawable.monalisa),
        Artwork("El grito", "Edvard Munch", "1893", R.drawable.grito),
        Artwork("La Noche Estrellada", "Vincent van Gogh", "1889", R.drawable.noche_estrellada),
    )

    val artwork = artworks.getOrNull(currentArtwork)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 15.dp)
    ) {
        artwork?.let { currentArtwork ->
            mainImage(currentArtwork.image)
            Spacer(modifier = modifier.padding(40.dp))
            infoCard(
                description = currentArtwork.description,
                title = currentArtwork.title,
                year = currentArtwork.year
            )
        }

        Spacer(modifier = modifier.padding(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    val previousArtwork = (currentArtwork - 1 + 3) % 3
                    currentArtwork = previousArtwork
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(text = "Preview", fontSize = 16.sp)
            }

            Button(
                onClick = {
                    val nextArtwork = (currentArtwork + 1) % 3
                    currentArtwork = nextArtwork
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(text = "Next", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun mainImage(image: Int, modifier: Modifier = Modifier) {
    val imagePainter = painterResource(image)

    Card(
        modifier = modifier
            .size(515.dp)
            .padding(top = 25.dp, start = 15.dp, end = 15.dp)
            .shadow(elevation = 20.dp),
        shape = RoundedCornerShape(size = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.padding(all = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun infoCard(description: String, title: String, year: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(all = 15.dp)
        ) {
            Column(
                modifier = modifier.padding(bottom = 3.dp)
            ) {
                Text(text = description, fontSize = 24.sp)
            }
            Row {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = "($year)")
            }
        }
    }
}

data class Artwork(val description: String, val title: String, val year: String, val image: Int)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        DisplayContent()
    }
}