package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.data.api.MovieApi
import com.example.droidpractice3.listwithdetails.data.database.MovieDatabase
import com.example.droidpractice3.listwithdetails.data.entity.MovieDbEntity
import com.example.droidpractice3.listwithdetails.data.mapper.MovieMapper
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper,
    private val database: MovieDatabase
) : IMovieRepository {

    override suspend fun getList(q: String, type: MovieType?) =
        withContext(Dispatchers.IO) {
            val response = api.getMovies(
                search = q,
                type = type?.name
            )
            if (response.response.not()) {
                throw Exception(response.error.orEmpty())
            }
            mapper.mapSearch(response)
        }

    override suspend fun getById(id: String) =
        withContext(Dispatchers.IO) {
            val response = api.getMovie(id)
            if (response.response.not()) {
                throw Exception(response.error.orEmpty())
            }
            mapper.mapFull(response)
        }


    override suspend fun saveFavorite(movie: MovieShortEntity) =
        withContext(Dispatchers.IO) {
            database.movieDao().insert(
                MovieDbEntity(
                    id = movie.id,
                    name = movie.title,
                    year = movie.year,
                    type = movie.type.name,
                    url = movie.posterUrl
                )
            )
        }

    override suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            database.movieDao().getAll().map {
                MovieShortEntity(
                    it.id.toString(),
                    it.name.orEmpty(),
                    it.year.orEmpty(),
                    MovieType.getByValue(it.type),
                    it.url.orEmpty()
                )
            }
        }

    override suspend fun deleteById(id: String) =
        withContext(Dispatchers.IO) {
            database.movieDao().deleteById(id)
        }
}
