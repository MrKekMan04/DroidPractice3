package com.example.droidpractice3.container.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidpractice3.container.presentation.state.ProfileState
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: IMovieRepository
) : ViewModel() {

    private var mutableState = MutableStateFlow(ProfileState())
    val viewState = mutableState.asStateFlow()

    init {
        updateFavorites()
    }

    fun onUpdateClick() {
        updateFavorites()
    }

    fun onItemDoubleClicked(id: String) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    private fun updateFavorites() {
        viewModelScope.launch {
            mutableState.update { it.copy(items = repository.getFavorites()) }
        }
    }
}
