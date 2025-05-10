package com.example.droidpractice3.listwithdetails.data.model

import com.google.gson.annotations.SerializedName

abstract class MovieApiBaseResponse {

    @SerializedName("Response")
    val response: Boolean = false

    @SerializedName("Error")
    val error: String? = null
}
