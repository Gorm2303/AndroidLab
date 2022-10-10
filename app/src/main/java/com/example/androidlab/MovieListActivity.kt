package com.example.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab.databinding.ActivityMovieListsBinding

class MovieListActivity : AppCompatActivity() {
    lateinit var binding : ActivityMovieListsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraString = intent.getStringExtra("EXTRA_STRING")
        Log.i("ButtonClicked", "${extraString}")
        binding.listTextView.text = extraString

        binding.listTextView
        
    }
}