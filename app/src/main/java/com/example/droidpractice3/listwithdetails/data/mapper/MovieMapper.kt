package com.example.droidpractice3.listwithdetails.data.mapper

import com.example.droidpractice3.listwithdetails.data.model.MovieFullResponse
import com.example.droidpractice3.listwithdetails.data.model.MoviesSearchResponse
import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType

class MovieMapper {

    fun mapSearch(response: MoviesSearchResponse) =
        response.search?.map { movie ->
            MovieShortEntity(
                id = movie.id.orEmpty(),
                title = movie.title.orEmpty(),
                type = MovieType.getByValue(movie.type),
                year = movie.year.orEmpty(),
                posterUrl = movie.posterUrl.orEmpty()
            )
        }.orEmpty()

    fun mapFull(response: MovieFullResponse) =
        MovieFullEntity(
            title = response.title.orEmpty(),
            imdbID = response.imdbID.orEmpty(),
            year = response.year.orEmpty(),
            plot = response.plot.orEmpty(),
            poster = response.poster.orEmpty(),
            ratings = response.ratings?.map {
                MovieFullEntity.Rating(
                    it.source.orEmpty(),
                    it.value.orEmpty()
                )
            }.orEmpty(),
            type = MovieType.getByValue(response.type)
        )
}
