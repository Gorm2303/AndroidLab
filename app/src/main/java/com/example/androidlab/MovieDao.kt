package com.example.androidlab

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE title LIKE :title LIMIT 1")
    suspend fun findByTitle(title: String): Movie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}