package com.example.droidpractice3.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.droidpractice3.container.presentation.viewmodel.DetailsViewModel
import com.example.droidpractice3.container.presentation.viewmodel.EditViewModel
import com.example.droidpractice3.container.presentation.viewmodel.FavouritesViewModel
import com.example.droidpractice3.container.presentation.viewmodel.ListViewModel
import com.example.droidpractice3.container.presentation.viewmodel.ProfileViewModel
import com.example.droidpractice3.listwithdetails.data.mapper.DataSourceProvider
import com.example.droidpractice3.listwithdetails.data.mapper.MovieMapper
import com.example.droidpractice3.listwithdetails.data.model.ProfileEntity
import com.example.droidpractice3.listwithdetails.data.repository.IMovieRepository
import com.example.droidpractice3.listwithdetails.data.repository.IProfileRepository
import com.example.droidpractice3.listwithdetails.data.repository.MoviesRepository
import com.example.droidpractice3.listwithdetails.data.repository.ProfileRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val rootModule = module {
    single { getSharedPrefs(androidApplication()) }
    single<SharedPreferences.Editor> { getSharedPrefs(androidApplication()).edit() }
    single { getDataStore(androidContext()) }
    single<IMovieRepository> { MoviesRepository(get(), get(), get()) }
    single<IProfileRepository> { ProfileRepository() }

    factory { MovieMapper() }
    factory<DataStore<ProfileEntity>>(named("profile")) { DataSourceProvider(get()).provide() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { FavouritesViewModel(get()) }
    viewModel { EditViewModel(get()) }
}

private const val dataStoreFileName: String = "default"

fun getSharedPrefs(androidApplication: Application): SharedPreferences =
    androidApplication.getSharedPreferences(dataStoreFileName, Context.MODE_PRIVATE)

fun getDataStore(androidContext: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create { androidContext.preferencesDataStoreFile(dataStoreFileName) }
