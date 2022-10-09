package com.example.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FindMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_movie)

        val title : String? = intent.getStringExtra("EXTRA_MOVIE")
        Log.d(title, "Title not found")
        val movie: Movie? = readData(title)

        val textView = findViewById<TextView>(R.id.movieTitle).apply {
            if (movie != null) {
                text = movie.title
            }
        }
    }

    fun readData(title: String?) : Movie? {
        var movie : Movie? = null
        val appDb : AppDatabase = AppDatabase.getDatabase(this)
        if (title == null) return null

        if (title.isNotEmpty()){

            GlobalScope.launch {

                movie = appDb.movieDao().findByTitle(title)
                Log.d("Robin Data",movie.toString())

            }
            return movie

        }else Toast.makeText(this@FindMovieActivity,"Please go back and enter the data", Toast.LENGTH_SHORT).show()
        return null
    }
}