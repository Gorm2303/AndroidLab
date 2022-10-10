package com.example.androidlab

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding;


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)
        initDataBase()
    }

    private fun initDataBase() {
        Thread {
            Log.i("ThreadDelete", "Thread deletes every movie in DB")
            appDb.movieDao().deleteAll()
        }.start()
        writeData("Spider man", 1482192000000L, "Spider man movie shoots his web from building to building")
        writeData("Peter Pan", 1482193000000L, "Peter Pan flies with is friends")
//        binding.btnPrintAllData.setOnClickListener {
//        }
    }

    fun listMovies(view: View) {
        val intent = Intent(this, MovieListActivity::class.java).apply {
            putExtra("EXTRA_STRING", view.getTag().toString())
        }
        startActivity(intent)
    }

    fun printAllMovies(view : View) {
        Thread {
            Log.i("ThreadPrintMovies", "Thread Prints all movies from the database")
            val movieList = appDb.movieDao().getAll()
            for (movie : Movie in movieList) {
                Log.i("ThreadPrintMovie", "Thread prints movie: $movie")
            }
        }.start()
    }

    private fun writeData(title: String, releaseInMiliSec: Long, overview : String) {
        val movie = Movie(
            (Math.random() * 1000000).toInt(), title, releaseInMiliSec, overview,
        )
        Thread {
            appDb.movieDao().insert(movie)
            Log.i(
                "MovieInsert",
                "Movie inserted: ${appDb.movieDao().findByTitle(movie.title)}"
            )
        }.start()
        Toast.makeText(this@MainActivity, "DB was successfully written", Toast.LENGTH_SHORT).show()

    }

    fun displaySearchResult(view: View) {
        val title = binding.findByTitle.text.toString()

        val intent = Intent(this, FindMovieActivity::class.java).apply {
            putExtra("EXTRA_MOVIE", title)
        }
        startActivity(intent)
    }
}