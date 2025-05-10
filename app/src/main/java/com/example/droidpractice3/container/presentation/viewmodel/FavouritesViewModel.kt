package com.example.droidpractice3.container.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidpractice3.container.presentation.state.FavouritesState
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val movieRepository: IMovieRepository
) : ViewModel() {

    private val mutableState = MutableFavouritesState()
    val viewState = mutableState as FavouritesState

    init {
        updateFavorites()
    }

    fun onUpdateClick() {
        updateFavorites()
    }

    fun onItemDoubleClicked(id: String) {
        viewModelScope.launch {
            movieRepository.deleteById(id)
        }
    }

    private fun updateFavorites() {
        viewModelScope.launch {
            mutableState.items = movieRepository.getFavorites()
        }
    }

    private class MutableFavouritesState : FavouritesState {
        override var items: List<MovieShortEntity> = mutableListOf()
    }
}
