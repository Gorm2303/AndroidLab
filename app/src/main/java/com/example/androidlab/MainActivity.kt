package com.example.androidlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.databinding.ActivityMainBinding;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


const val EXTRA_MESSAGE = "com.example.androidlab.MESSAGE"

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener {
            writeData()
        }

        binding.btnDeleteData.setOnClickListener {

            GlobalScope.launch {

                appDb.movieDao().deleteAll()

            }

        }
    }

    private fun writeData(){
        val title = "Spider man"

        if(title.isNotEmpty()) {
            val student = Movie(
                null, title
            )
            GlobalScope.launch(Dispatchers.IO) {

                appDb.movieDao().insert(student)
            }

            Toast.makeText(this@MainActivity,"Successfully written",Toast.LENGTH_SHORT).show()
        }else Toast.makeText(this@MainActivity,"PLease Enter Data",Toast.LENGTH_SHORT).show()

    }

    fun readData(view: View) {
        val title = R.id.findByTitle.toString() //not working

        if (title.isNotEmpty()){

            lateinit var movie : Movie

            GlobalScope.launch {

                movie = appDb.movieDao().findByTitle(title)
                Log.d("Robin Data",movie.toString())
                displayData(movie)

            }

        }else Toast.makeText(this@MainActivity,"Please enter the data", Toast.LENGTH_SHORT).show()
    }

    fun displayData(movie: Movie) {

        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, movie.title)
        }
        startActivity(intent)
    }
}