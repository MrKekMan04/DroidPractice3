package com.example.droidpractice3.container.presentation.state

import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity


data class ProfileState(

    val items: List<MovieShortEntity> = emptyList()
)
