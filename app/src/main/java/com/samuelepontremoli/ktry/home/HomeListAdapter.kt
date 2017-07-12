package com.samuelepontremoli.ktry.home

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.utils.inflate
import com.samuelepontremoli.ktry.utils.loadFromUrl
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.views.RatioImageView
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 01/07/17.
 * Trending Gifs Adapter
 */
class HomeListAdapter(var listGifs: MutableList<GiphyGif>) : RecyclerView.Adapter<HomeListAdapter.GifHolder>() {

    private val logger = AnkoLogger("HomeListAdapter")

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        holder.bind(listGifs[position])
    }

    override fun getItemCount(): Int {
        return listGifs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val v = parent.inflate(R.layout.item_gif)
        return GifHolder(v)
    }

    fun setList(lista: MutableList<GiphyGif>) {
        listGifs = lista
    }

    class GifHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val gifView: RatioImageView? = itemView?.findViewById(R.id.gifImage)

        fun bind(gif: GiphyGif) {
            gifView?.setHeightRatio(gif.images.fixedHeightSmall.getHeightScale())
            gifView?.loadFromUrl(gif.images.fixedHeightSmall.url)
        }

    }

}