package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity

interface IMovieRepository {

    fun getList(q: String = ""): List<MovieShortEntity>

    fun getById(id: String): MovieFullEntity?
}
