package com.example.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.androidlab.databinding.ActivityFindMovieBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date

class FindMovieActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title : String? = intent.getStringExtra("EXTRA_MOVIE")
        Log.d("TitleIs", title.toString())
        GlobalScope.launch {
            val movie: Movie? = readData(title)

            Log.d("MNF", "Movie is $movie")

            if (movie != null) {
                binding.movieTitle.text = movie.title
                binding.movieRelease.text = Date(movie.releaseDate).toString()
            }
        }
    }

    private suspend fun readData(title: String?) : Movie? {
        var movie : Movie? = null
        val appDb : AppDatabase = AppDatabase.getDatabase(this)
        if (title == null) return null

        if (title.isNotEmpty()){
            movie = appDb.movieDao().findByTitle(title)
            Log.d("Robin Data",movie.toString())

        }else Toast.makeText(this@FindMovieActivity,"Please go back and enter the data", Toast.LENGTH_SHORT).show()
        return movie
    }
}