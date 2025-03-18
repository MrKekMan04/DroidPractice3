package com.example.droidpractice3.listwithdetails.domain.entity

import androidx.annotation.StringRes
import com.example.droidpractice3.R

class MovieShortEntity(
    val id: String,
    val title: String,
    val year: String,
    val type: MovieType,
    val posterUrl: String,
)

enum class MovieType(@StringRes val stringRes: Int) {
    ANIME(R.string.anime)
}
