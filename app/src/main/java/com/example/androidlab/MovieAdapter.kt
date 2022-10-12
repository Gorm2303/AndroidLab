package com.example.androidlab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MovieAdapter(private val data: LinkedList<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val itemListener: RecyclerViewClickListener? = null

    inner class ViewHolder(item : View): RecyclerView.ViewHolder(item){
        val employeeName : TextView = item.findViewById(R.id.name)

        fun onClick(v: View?) {
            itemListener!!.recyclerViewListClicked(v, this.layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.employeeName.text = data[i].title
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
