package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity

interface IMovieRepository {

    suspend fun getList(q: String): List<MovieShortEntity>

    suspend fun getById(id: String): MovieFullEntity?
}
