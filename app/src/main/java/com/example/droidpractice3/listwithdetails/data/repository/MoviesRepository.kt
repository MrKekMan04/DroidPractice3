package com.example.droidpractice3.listwithdetails.data.repository

import com.example.droidpractice3.listwithdetails.data.mock.MoviesData

class MoviesRepository : IMovieRepository {

    override fun getList(q: String) =
        MoviesData.moviesShort.filter { it.title.contains(q, ignoreCase = true) }

    override fun getById(id: String) = MoviesData.moviesFull.find { it.imdbID == id }
}
