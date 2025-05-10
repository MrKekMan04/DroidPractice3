package com.example.droidpractice3.container.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidpractice3.container.presentation.screen.DetailsScreen
import com.example.droidpractice3.container.presentation.state.MoviesListState
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType
import com.example.droidpractice3.util.launchLoadingAndError
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import org.koin.java.KoinJavaComponent.inject
import java.time.Duration

class ListViewModel(
    private val repository: IMovieRepository,
    private val navigation: StackNavContainer
) : ViewModel() {

    private val dataStore: DataStore<Preferences> by inject(DataStore::class.java)
    private val typesKey = stringPreferencesKey(KEY_MOVIE_TYPE)

    private val mutableState = MutableMoviesListState()
    val viewState = mutableState as MoviesListState
    private val textChangesFlow = MutableStateFlow("")
    private var filterType: MovieType? = null

    init {
        viewModelScope.launch {
            textChangesFlow
                .debounce(Duration.ofSeconds(1L))
                .collect { loadMovies() }
        }

        viewModelScope.launch {
            dataStore.data.collect {
                filterType = it[typesKey]
                    ?.let { MovieType.getByValue(it) }
                updateBadge()
            }
        }

        mutableState.typesAvailable = MovieType.entries.toSet()
    }

    private fun loadMovies() {
        val query = textChangesFlow.value

        mutableState.items = emptyList()
        mutableState.error = null

        if (query.length < MIN_QUERY_LENGTH_TO_SEARCH) {
            return
        }

        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
            mutableState.items = repository.getList(query, filterType)
        }
    }

    fun onQueryChanged(query: String) {
        mutableState.query = query
        viewModelScope.launch { textChangesFlow.emit(query) }
    }

    fun onItemClicked(id: String) {
        navigation.forward(DetailsScreen(movieId = id))
    }

    fun onSelectionDialogDismissed() {
        mutableState.showTypesDialog = false
    }

    fun onSelectedVariantChanged(variant: MovieType, selected: Boolean) {
        mutableState.selectedType = mutableState.selectedType.run {
            if (selected) variant else null
        }
    }

    fun onFiltersConfirmed() {
        if (filterType != mutableState.selectedType) {
            filterType = mutableState.selectedType
            loadMovies()
            updateBadge()

            viewModelScope.launch {
                dataStore.edit {
                    if (filterType != null)
                        it[typesKey] = filterType!!.name
                    else
                        it.remove(typesKey)
                }
            }
        }
        onSelectionDialogDismissed()
    }

    private fun updateBadge() {
        mutableState.hasBadge = filterType != null
    }

    fun onFiltersClicked() {
        mutableState.showTypesDialog = true
        mutableState.selectedType = filterType
    }

    fun onItemDoubleClicked(item: MovieShortEntity) {
        viewModelScope.launch {
            repository.saveFavorite(item)
        }
    }

    private class MutableMoviesListState : MoviesListState {

        override var items: List<MovieShortEntity> by mutableStateOf(emptyList())
        override var query by mutableStateOf("")
        override val isEmpty get() = items.isEmpty()
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
        override var showTypesDialog: Boolean by mutableStateOf(false)
        override var typesAvailable: Set<MovieType> by mutableStateOf(emptySet())
        override var selectedType: MovieType? by mutableStateOf(null)
        override var hasBadge: Boolean by mutableStateOf(false)
    }

    companion object {
        private const val MIN_QUERY_LENGTH_TO_SEARCH = 3
        private const val KEY_MOVIE_TYPE = "MOVIE_TYPES"
    }
}
