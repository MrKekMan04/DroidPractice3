package com.example.droidpractice3.container.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.droidpractice3.container.presentation.state.MovieDetailState
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back

class DetailsViewModel(
    repository: IMovieRepository,
    private val navigation: StackNavContainer,
    id: String
) : ViewModel() {

    private val mutableState = MutableDetailsState()
    val viewState = mutableState as MovieDetailState

    init {
        mutableState.movie = repository.getById(id)
    }

    fun back() {
        navigation.back()
    }

    fun onRatingChanged(rating: Float) {
        mutableState.rating = rating
    }

    private class MutableDetailsState : MovieDetailState {

        override var movie: MovieFullEntity? by mutableStateOf(null)
        override var rating: Float by mutableFloatStateOf(0f)
        override val isRatingVisible: Boolean get() = rating != 0f
    }
}
