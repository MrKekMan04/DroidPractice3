package com.example.droidpractice3.listwithdetails.domain.entity

data class MovieFullEntity(
    val plot: String,
    val poster: String,
    val ratings: List<Rating>,
    val title: String,
    val type: MovieType,
    val year: String,
    val imdbID: String,
) {
    data class Rating(
        val source: String,
        val value: String
    )
}
