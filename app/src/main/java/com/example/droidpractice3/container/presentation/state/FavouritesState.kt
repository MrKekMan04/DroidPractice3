package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity

interface FavouritesState {
    val items: List<MovieShortEntity>
}
