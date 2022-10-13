package com.example.androidlab

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MovieAdapter(
    private val data: ArrayList<Movie>,
    private val context : Context,
    private val itemListener: RecyclerViewClickListener
    ) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {



    inner class ViewHolder(item : View): RecyclerView.ViewHolder(item), View.OnClickListener{
        val movieName : TextView = item.findViewById(R.id.name)

        override fun onClick(v: View?) {
            itemListener.recyclerViewListClicked(v, this.layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.movieName.text = data[i].title
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
