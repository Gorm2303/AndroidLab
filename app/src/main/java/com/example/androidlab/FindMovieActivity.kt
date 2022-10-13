package com.example.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.androidlab.databinding.ActivityFindMovieBinding
import java.sql.Date

class FindMovieActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title : String? = intent.getStringExtra("EXTRA_MOVIE")
        Log.i("IntentGetExtra", "Title of movie is: " + title.toString())
        Thread {
            val movie: Movie? = readData(title)

            Log.i("MovieRead", "Movie is $movie")

            if (movie != null) {
                binding.movieTitle.text = "${movie.title}"
                binding.release.text = "Released: ${Date(movie.releaseDate)}"
                binding.runtime.text = "Runtime: ${movie.runtime} minutes"
                //binding.movieImageView
                binding.overviewText.text = "Overview: \n${movie.overview}"
                binding.originalLanguage.text = "Language: ${movie.originalLanguage}"
                binding.budget.text = "Budget: ${movie.budget}$ million"
                binding.revenue.text = "Revenue: ${movie.revenue}$ million"
            }
        }.start()
    }

    private fun readData(title: String?) : Movie? {
        val movie: Movie?
        val appDb = AppDatabase.getDatabase(this)
        Looper.prepare()
        if (title == null) {
            Toast.makeText(this@FindMovieActivity,"Please go back and enter the data", Toast.LENGTH_SHORT).show()
            return null
        }
        movie = appDb.movieDao().findByTitle(title)
        if (movie == null) {
            Toast.makeText(this@FindMovieActivity,"Please go back and enter the data", Toast.LENGTH_SHORT).show()
            return null
        }
        Log.i("Robin Data", movie.title)

        return movie
    }
}