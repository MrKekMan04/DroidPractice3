package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType

interface MoviesListState {

    val items: List<MovieShortEntity>
    val query: String
    val isEmpty: Boolean
    val isLoading: Boolean
    val error: String?
    val hasBadge: Boolean
    val showTypesDialog: Boolean
    val typesAvailable: Set<MovieType>
    val selectedType: MovieType?
}