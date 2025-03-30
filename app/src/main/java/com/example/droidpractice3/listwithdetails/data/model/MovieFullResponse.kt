package com.example.droidpractice3.listwithdetails.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class MovieFullResponse(

    val imdbID: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Year")
    val year: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Ratings")
    val ratings: List<RatingResponse>?
) : MovieApiBaseResponse()

class RatingResponse(

    @SerializedName("Source")
    val source: String?,
    @SerializedName("Value")
    val value: String?
)
