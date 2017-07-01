package com.samuelepontremoli.ktry.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.inflate
import com.samuelepontremoli.ktry.loadFromUrl
import com.samuelepontremoli.ktry.network.GiphyGif
import kotlinx.android.synthetic.main.gif_item.view.*

/**
 * Created by samuele on 01/07/17.
 * Home Adapter
 */
class HomeAdapter(listGifs: List<GiphyGif>) : RecyclerView.Adapter<HomeAdapter.GifHolder>() {

    val listGifs: List<GiphyGif>

    init {
        this.listGifs = listGifs
    }

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        holder.gifView.loadFromUrl(listGifs.get(position).images.fixedHeight.url)
    }

    override fun getItemCount(): Int {
        return listGifs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val v = parent.inflate(R.layout.gif_item)
        return GifHolder(v)
    }

    class GifHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifView: ImageView

        init {
            gifView = itemView.gif_image
        }
    }

}