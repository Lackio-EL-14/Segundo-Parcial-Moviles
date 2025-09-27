package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.tflite.support.Empty
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesScreen(
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel()
) {
    val state = popularMoviesViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        popularMoviesViewModel.fetchPopularMovies()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val s = state.value) {
            is PopularMoviesViewModel.UiState.Error -> {
                Text(
                    text = s.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is PopularMoviesViewModel.UiState.Loading -> {
                CircularProgressIndicator()
            }


            is PopularMoviesViewModel.UiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Películas Populares",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Última actualización: ${s.lastUpdate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PopularMoviesView(
                        movies = s.movies,
                        onLikeClick = { movie ->
                            popularMoviesViewModel.toggleLike(movie)
                        }
                    )

                }
            }
        }
    }
}
