package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PopularMoviesViewModel(
    private val fetchPopularMovies: FetchPopularMoviesUseCase,
    private val repo: MovieRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val movies: List<MovieModel>,
            val lastUpdate: String
        ) : UiState()

        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            val result = fetchPopularMovies.invoke()
            result.fold(
                onSuccess = { movies ->
                    val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
                    _state.value = UiState.Success(
                        movies = movies,
                        lastUpdate = currentTime
                    )
                },
                onFailure = { _state.value = UiState.Error("Error al cargar películas") }
            )
        }
    }

    fun toggleLike(movie: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.toggleLike(movie)
            fetchPopularMovies()
        }
    }
}
