package com.example.droidpractice3.listwithdetails.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.droidpractice3.listwithdetails.data.dao.MovieDao
import com.example.droidpractice3.listwithdetails.data.entity.MovieDbEntity

@Database(
    entities = [MovieDbEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
