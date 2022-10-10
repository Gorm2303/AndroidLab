package com.example.androidlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener {
            writeData("Spider man", 1482192000000L)
            writeData("Peter Pan", 1482193000000L)

        }

        binding.btnDeleteData.setOnClickListener {
            GlobalScope.launch {
                appDb.movieDao().deleteAll()
            }
        }
    }

    private fun writeData(title : String, releaseInMiliSec : Long) {
        val movie = Movie(
            null, title, releaseInMiliSec
        )
        GlobalScope.launch(Dispatchers.IO) {

            appDb.movieDao().insert(movie)
            Log.d(
                "IsInsertedInDB?",
                "Movie inserted: ${appDb.movieDao().findByTitle(movie.title.toString())}"
            )

        }
        Toast.makeText(this@MainActivity, "Successfully written", Toast.LENGTH_SHORT).show()

    }
    fun displayData(view: View) {
        val title = binding.findByTitle.text.toString()

        val intent = Intent(this, FindMovieActivity::class.java).apply {
            putExtra("EXTRA_MOVIE", title)
        }
        startActivity(intent)
    }
}