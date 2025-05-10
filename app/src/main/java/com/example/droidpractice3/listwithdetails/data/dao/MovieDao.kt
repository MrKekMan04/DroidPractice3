package com.example.droidpractice3.listwithdetails.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.droidpractice3.listwithdetails.data.entity.MovieDbEntity

@Dao
interface MovieDao {

    @Query("select * from MovieDbEntity")
    suspend fun getAll(): List<MovieDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: MovieDbEntity)

    @Query(
        """
        delete from MovieDbEntity
        where id = :id
        """
    )
    suspend fun deleteById(id: String)
}
