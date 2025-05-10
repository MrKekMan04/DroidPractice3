package com.example.droidpractice3.di


import android.content.Context
import androidx.room.Room
import com.example.droidpractice3.listwithdetails.data.database.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

private const val movieDatabaseName: String = "movies"

object DatabaseBuilder {
    private var INSTANCE: MovieDatabase? = null

    fun getInstance(context: Context): MovieDatabase {
        if (INSTANCE == null) {
            synchronized(MovieDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRoomDatabase(context)
                }
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            movieDatabaseName
        ).build()
}
