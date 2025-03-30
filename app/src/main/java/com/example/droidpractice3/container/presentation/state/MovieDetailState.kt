package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity

interface MovieDetailState {

    val movie: MovieFullEntity?
    val rating: Float
    val isRatingVisible: Boolean
    val isLoading: Boolean
    val error: String?
    val related: List<MovieShortEntity>
}