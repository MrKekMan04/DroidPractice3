package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType

interface IMovieRepository {

    suspend fun getList(q: String, type: MovieType? = null): List<MovieShortEntity>

    suspend fun getById(id: String): MovieFullEntity?

    suspend fun saveFavorite(movie: MovieShortEntity)

    suspend fun getFavorites(): List<MovieShortEntity>

    suspend fun deleteById(id: String)
}
