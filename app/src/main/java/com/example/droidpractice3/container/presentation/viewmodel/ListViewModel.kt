package com.example.droidpractice3.container.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.droidpractice3.container.presentation.screen.DetailsScreen
import com.example.droidpractice3.container.presentation.state.MoviesListState
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward

class ListViewModel(
    private val repository: IMovieRepository,
    private val navigation: StackNavContainer
) : ViewModel() {

    private val mutableState = MutableMoviesListState()
    val viewState = mutableState as MoviesListState

    init {
        loadMovies()
    }

    private fun loadMovies() {
        mutableState.items = repository.getList(viewState.query)
    }

    fun onQueryChanged(query: String) {
        mutableState.query = query
        loadMovies()
    }

    fun onItemClicked(id: String) {
        navigation.forward(DetailsScreen(movieId = id))
    }

    private class MutableMoviesListState : MoviesListState {

        override var items: List<MovieShortEntity> by mutableStateOf(emptyList())
        override var query by mutableStateOf("")
        override val isEmpty get() = items.isEmpty()
    }
}
