package com.example.droidpractice3.listwithdetails.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDbEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "movieName")
    val name: String?,

    @ColumnInfo(name = "movieYear")
    val year: String?,

    @ColumnInfo(name = "movieType")
    val type: String?,

    @ColumnInfo(name = "movieUrl")
    val url: String?
)
