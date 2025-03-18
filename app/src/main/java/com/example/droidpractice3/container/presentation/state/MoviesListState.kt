package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity

interface MoviesListState {

    val items: List<MovieShortEntity>
    val query: String
    val isEmpty: Boolean
}