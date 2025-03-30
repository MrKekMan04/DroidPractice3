package com.example.droidpractice3.di

import com.example.droidpractice3.container.presentation.viewmodel.DetailsViewModel
import com.example.droidpractice3.container.presentation.viewmodel.ListViewModel
import com.example.droidpractice3.listwithdetails.data.mapper.MovieMapper
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.data.repository.MoviesRepository
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    single<IMovieRepository> { MoviesRepository(get(), get()) }

    factory { MovieMapper() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
}
