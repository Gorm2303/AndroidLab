package com.example.androidlab

import android.media.Image
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: Long,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String,
    @ColumnInfo(name = "budget") val budget: Int,
    @ColumnInfo(name = "revenue") val revenue: Int,
    )