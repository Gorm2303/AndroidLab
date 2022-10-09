package com.example.androidlab

import android.content.Intent
import android.os.Bundle
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
        val releaseDate = 1000000000L

        if(title.isNotEmpty()) {
            val student = Movie(
                null, title, releaseDate
            )
            GlobalScope.launch(Dispatchers.IO) {

                appDb.movieDao().insert(student)
            }

            Toast.makeText(this@MainActivity,"Successfully written",Toast.LENGTH_SHORT).show()
        }else Toast.makeText(this@MainActivity,"PLease Enter Data",Toast.LENGTH_SHORT).show()

    }



    fun displayData(view: View) {
        val title = binding.findByTitle.text.toString()

        val intent = Intent(this, FindMovieActivity::class.java).apply {
            putExtra("EXTRA_MOVIE", title)
        }
        startActivity(intent)
    }
}