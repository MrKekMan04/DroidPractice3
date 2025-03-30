package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.data.api.MovieApi
import com.example.droidpractice3.listwithdetails.data.mapper.MovieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val api: MovieApi,
    private val mapper: MovieMapper
) : IMovieRepository {

    override suspend fun getList(q: String) =
        withContext(Dispatchers.IO) {
            val response = api.getMovies(q)
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
}
