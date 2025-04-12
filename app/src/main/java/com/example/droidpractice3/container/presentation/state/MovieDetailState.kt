package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity

interface MovieDetailState {

    val movie: MovieFullEntity?
    val rating: Float
    val isRatingVisible: Boolean
}