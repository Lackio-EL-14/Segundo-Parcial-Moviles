package com.calyrsoft.ucbp1.features.movie.data.repository

import com.calyrsoft.ucbp1.features.movie.data.database.dao.MovieDao
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository

class MovieRepository(
    private val remote: MovieRemoteDataSource,
    private val dao: MovieDao
) : IMoviesRepository {

    override suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        val result = remote.fetchPopularMovies()
        result.onSuccess { movies ->

            val localMovies = dao.getAllMovies()
            val merged = movies.map { remoteMovie ->
                val local = localMovies.find { it.title == remoteMovie.title }
                MovieEntity(
                    pathUrl = remoteMovie.pathUrl,
                    title = remoteMovie.title,
                    isLiked = local?.isLiked ?: false
                )
            }
            dao.insertMovies(merged)
        }

        val stored = dao.getAllMovies().map { entity ->
            MovieModel(entity.pathUrl, entity.title, entity.isLiked)
        }
        return Result.success(stored)
    }

    suspend fun toggleLike(movie: MovieModel) {
        val entity = MovieEntity(
            pathUrl = movie.pathUrl,
            title = movie.title,
            isLiked = !movie.isLiked
        )
        dao.updateMovie(entity)
    }
}