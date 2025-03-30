package com.example.droidpractice3.listwithdetails.data.api

import com.example.droidpractice3.listwithdetails.data.model.MovieFullResponse
import com.example.droidpractice3.listwithdetails.data.model.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    suspend fun getMovies(
        @Query("s") search: String,
        @Query("page") page: Int = 1
    ): MoviesSearchResponse

    @GET("/")
    suspend fun getMovie(
        @Query("i") id: String? = null,
    ): MovieFullResponse
}
