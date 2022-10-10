package com.example.androidlab

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): Movie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()
}