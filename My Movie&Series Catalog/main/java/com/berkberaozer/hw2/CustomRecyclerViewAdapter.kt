package com.berkberaozer.hw2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.berkberaozer.hw2.Constants.MOVIE
import com.berkberaozer.hw2.Constants.SERIES

class CustomRecyclerViewAdapter( private val context: Context, private var recyclerItemValues: ArrayList<Show>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun setData(items:List<Show>){
        recyclerItemValues = items as ArrayList<Show>
        notifyDataSetChanged()
    }

    interface RecyclerAdapterInterface{
        fun editItem(sc:Show)
    }
    lateinit var recyclerAdapterInterface:RecyclerAdapterInterface

    init {
        recyclerAdapterInterface = context as RecyclerAdapterInterface
    }

    override fun getItemViewType(position: Int): Int {
        val sc = recyclerItemValues[position]
        return sc.type
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflator = LayoutInflater.from(viewGroup.context)

        return if (viewType == MOVIE) {
            itemView = inflator.inflate(R.layout.recycler_movie_item, viewGroup, false)
            MovieCustomRecyclerViewItemHolder(itemView)
        } else {
            itemView = inflator.inflate(R.layout.recycler_series_item, viewGroup, false)
            SeriesCustomRecyclerViewItemHolder(itemView)
        }
    }

    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = recyclerItemValues[position]

        if (getItemViewType(position) == MOVIE) {
            val itemView = myRecyclerViewItemHolder as MovieCustomRecyclerViewItemHolder
            itemView.tvMovieName.text = currentItem.name
            itemView.tvMovieDate.text = currentItem.date
            itemView.tvMovieScore.text = currentItem.score.toString()

            itemView.movieEdit.setOnClickListener {
                recyclerAdapterInterface.editItem(currentItem)
            }
        } else if (getItemViewType(position) == SERIES) {
            val itemView = myRecyclerViewItemHolder as SeriesCustomRecyclerViewItemHolder
            itemView.tvSeriesName.text = currentItem.name
            itemView.tvSeriesDate.text = currentItem.date
            itemView.tvSeriesScore.text = currentItem.score.toString()

            itemView.seriesEdit.setOnClickListener {
                recyclerAdapterInterface.editItem(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    internal inner class MovieCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvMovieName: TextView
        var tvMovieDate: TextView
        var tvMovieScore: TextView
        var movieEdit: Button
        var itemMovieConstraintLayout: ConstraintLayout

        init {
            tvMovieName = itemView.findViewById<TextView>(R.id.movieName)
            tvMovieDate = itemView.findViewById<TextView>(R.id.movieDate)
            tvMovieScore = itemView.findViewById<TextView>(R.id.movieScore)
            movieEdit = itemView.findViewById<Button>(R.id.movieEdit)
            itemMovieConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.itemMovieConstraintLayout)
        }
    }

    internal inner class SeriesCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvSeriesName: TextView
        var tvSeriesDate: TextView
        var tvSeriesScore: TextView
        var seriesEdit: Button
        var itemSeriesConstraintLayout: ConstraintLayout

        init {
            tvSeriesName = itemView.findViewById<TextView>(R.id.seriesName)
            tvSeriesDate = itemView.findViewById<TextView>(R.id.seriesDate)
            tvSeriesScore = itemView.findViewById<TextView>(R.id.seriesScore)
            seriesEdit = itemView.findViewById<Button>(R.id.seriesEdit)
            itemSeriesConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.itemSeriesConstraintLayout)
        }
    }
}

