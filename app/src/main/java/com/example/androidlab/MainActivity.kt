package com.example.androidlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidlab.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), RecyclerViewClickListener {
    lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase
    private var movieList = LinkedList<Movie>()
    lateinit var adapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)
        //initDataBase()
        initRecycleView()
    }

    private fun initRecycleView() {
        Thread {
            for (movie : Movie in appDb.movieDao().getAll()) {
                movieList.add(movie)
            }
        }.start()
            adapter = MovieAdapter(movieList, this, this)

        val recyclerView: RecyclerView = findViewById(R.id.movieRecyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }


    override fun recyclerViewListClicked(v: View?, position: Int) {
        Log.i("OnClickTest", "It clicked")

    }

    private fun initDataBase() {
        Thread {
            Log.i("ThreadDelete", "Thread deletes every movie in DB")
            appDb.movieDao().deleteAll()
            writeData("Spider man", 1482192000000L, "Spider man movie shoots his web from building to building")
            writeData("Peter Pan", 1483193000000L, "Peter Pan flies with is friends")
            writeData("Peter San", 1484194000000L, "Peter San flies with is friends")
            writeData("Peter Dan", 1485195000000L, "Peter Dan flies with is friends")
            writeData("Peter Fan", 1486196000000L, "Peter Fan flies with is friends")
            writeData("Peter Can", 1487197000000L, "Peter Can flies with is friends")
        }.start()

//        binding.btnPrintAllData.setOnClickListener {
//        }
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
        appDb.movieDao().insert(movie)
        Log.i(
            "MovieInsert",
            "Movie inserted: ${appDb.movieDao().findByTitle(movie.title)}"
        )
    }

    fun displaySearchResult(view: View) {
        val title = binding.findByTitle.text.toString()

        val intent = Intent(this, FindMovieActivity::class.java).apply {
            putExtra("EXTRA_MOVIE", title)
        }
        startActivity(intent)
    }

}