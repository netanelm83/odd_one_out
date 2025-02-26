package com.netgames.oddoneout.ui.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import com.netgames.oddoneout.di.provideImageLoader
import com.netgames.oddoneout.ui.components.CachedImage

@Composable
fun GameScreen(navController: NavController, gameViewModel: GameViewModel = hiltViewModel()) {
    val question by gameViewModel.currentQuestion.observeAsState()
    val isLoading by gameViewModel.isLoading.observeAsState(true)
    val feedback by gameViewModel.feedBack.observeAsState()
    val userStats by gameViewModel.userStats.observeAsState()

    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Find the Odd One Out!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            question?.let { q ->
                LazyVerticalGrid( columns = GridCells.Fixed(2)) {
                    items(q.images.size) { index ->
                        val image = q.images[index]
                        ImageCard(image, imageLoader) {
                            gameViewModel.checkAnswer(image)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        feedback?.let {
            Text(text = it, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(16.dp))
        userStats?.let {
            Text(text = "Games Played: ${it.gamesPlayed}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = "Correct Answers: ${it.correctAnswers}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = "Accuracy: ${String.format("%.2f", it.accuracy)}%", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ImageCard(imageUrl: String, imageLoader : ImageLoader,  onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        CachedImage(imageUrl, imageLoader = imageLoader, modifier = Modifier.fillMaxSize())
    }
}