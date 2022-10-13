package com.example.androidlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.ColumnInfo
import com.example.androidlab.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), RecyclerViewClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase
    private var movieList = ArrayList<Movie>()
    private lateinit var adapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this

        runBlocking {
            appDb = AppDatabase.getDatabase(context)
            Thread {
                movieList.addAll(appDb.movieDao().getAll())
                if (movieList.isEmpty()) {
                    initDataBase()
                    movieList.addAll(appDb.movieDao().getAll())
                } else {
                    Log.i("GetMovies", "Found movies in DB: $movieList")
                }
            }.start()
            initRecycleView()
        }
    }

    private fun initRecycleView() {
        adapter = MovieAdapter(movieList, this, this)
        Log.i("MakeRecycleView", "List used: $movieList")
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
            runBlocking {
                Log.i("ThreadDelete", "Thread deletes every movie in DB")
                appDb.movieDao().deleteAll()
                writeData(
                    "Spider man", 1482192000000L, 133,
                    "Spider-Man: Homecoming is a 2017 American superhero film based on the Marvel Comics character Spider-Man, co-produced by Columbia Pictures and Marvel Studios, and distributed by Sony Pictures Releasing. It is the second Spider-Man film reboot and the 16th film in the Marvel Cinematic Universe (MCU).",
                    "Eng", 175, 880,
                )
                writeData(
                    "Peter Pan", 1483193000000L, 113,
                    "Peter Pan is a 2003 fantasy adventure film directed by P.J. Hogan and written by Hogan and Michael Goldenberg. The screenplay is based on the 1904 play and 1911 novel Peter Pan, or The Boy Who Wouldn't Grow Up by J.M. Barrie. Jason Isaacs plays the dual roles of Captain Hook and George Darling, Olivia Williams plays Mary Darling, while Jeremy Sumpter plays Peter Pan, Rachel Hurd-Wood plays Wendy Darling, and Ludivine Sagnier plays Tinker Bell. Lynn Redgrave plays a supporting role as Aunt Millicent, a new character created for the film.",
                    "Eng", 130, 122,
                )
                writeData(
                    "Dumbo", 1484194000000L, 112,
                    "Dumbo is a 2019 American fantasy period adventure film directed by Tim Burton from a screenplay written by Ehren Kruger. The film is a live-action adaptation and reimagining of Walt Disney's 1941 animated film of the same name, which is based on the novel by Helen Aberson and Harold Pearl. The film stars Colin Farrell, Michael Keaton, Danny DeVito, Eva Green and Alan Arkin. The story follows a family that works at a failing traveling circus as they encounter a baby elephant with extremely large ears who is capable of flying.",
                    "Eng", 170, 353,
                )
                writeData(
                    "Star Wars", 1485195000000L, 121,
                    "Star Wars (retroactively titled Star Wars: Episode IV – A New Hope) is a 1977 American epic space opera film written and directed by George Lucas, produced by Lucasfilm and distributed by 20th Century Fox. It is the first film in the Star Wars film series and fourth chronological chapter of the \"Skywalker Saga\". Set \"a long time ago\" in a fictional universe where the galaxy is ruled by the tyrannical Galactic Empire, the story focuses on a group of freedom fighters known as the Rebel Alliance, who aim to destroy the Empire's newest weapon, the Death Star. Luke Skywalker becomes caught in the conflict while learning the ways of a metaphysical power known as \"the Force\" from Jedi Master Obi-Wan Kenobi. The cast includes Mark Hamill, Harrison Ford, Carrie Fisher, Peter Cushing, Alec Guinness, David Prowse, James Earl Jones, Anthony Daniels, Kenny Baker, and Peter Mayhew.",
                    "Eng", 11, 775,
                )
                writeData(
                    "Lord of The Rings", 1486196000000L, 178,
                    "The Lord of the Rings: The Fellowship of the Ring is a 2001 epic fantasy adventure film directed by Peter Jackson from a screenplay by Fran Walsh, Philippa Boyens, and Jackson, based on 1954's The Fellowship of the Ring, the first volume of the novel The Lord of the Rings by J. R. R. Tolkien. The film is the first installment in The Lord of the Rings trilogy. It features an ensemble cast including Elijah Wood, Ian McKellen, Liv Tyler, Viggo Mortensen, Sean Astin, Cate Blanchett, John Rhys-Davies, Billy Boyd, Dominic Monaghan, Orlando Bloom, Christopher Lee, Hugo Weaving, Sean Bean, Ian Holm, and Andy Serkis. Set in Middle-earth, the story tells of the Dark Lord Sauron, who seeks the One Ring, which contains part of his might, to return to power. The Ring has found its way to the young hobbit Frodo Baggins. The fate of Middle-earth hangs in the balance as Frodo and eight companions (who form the Fellowship of the Ring) begin their journey to Mount Doom in the land of Mordor, the only place where the Ring can be destroyed.",
                    "Eng", 93, 897,
                )
                writeData(
                    "Avatar", 1487197000000L, 162,
                    "Avatar (also marketed as James Cameron's Avatar) is a 2009 American[7][8] epic science fiction film directed, written, produced, and co-edited by James Cameron and starring Sam Worthington, Zoe Saldana, Stephen Lang, Michelle Rodriguez, and Sigourney Weaver. It is set in the mid-22nd century when humans are colonizing Pandora, a lush habitable moon of a gas giant in the Alpha Centauri star system, in order to mine the valuable mineral unobtanium.[9][10][11] The expansion of the mining colony threatens the continued existence of a local tribe of Na'vi – a humanoid species indigenous to Pandora. The film's title refers to a genetically engineered Na'vi body operated from the brain of a remotely located human that is used to interact with the natives of Pandora.",
                    "Eng", 237, 2950,
                )
            }
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

    private fun writeData(
        title: String,
        releaseInMiliSec: Long,
        runtime: Int,
        overview: String,
        originalLanguage: String,
        budget: Int,
        revenue: Int,) {

        val movie = Movie(
            (Math.random() * 1000000).toInt(),
            title,
            releaseInMiliSec,
            runtime,
            overview,
            originalLanguage,
            budget,
            revenue,
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