package com.samuelepontremoli.ktry.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.inflate
import com.samuelepontremoli.ktry.loadFromUrl
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.RatioImageView
import kotlinx.android.synthetic.main.gif_item.view.*

/**
 * Created by samuele on 01/07/17.
 * Home Adapter
 */
class HomeAdapter(val listGifs: List<GiphyGif>) : RecyclerView.Adapter<HomeAdapter.GifHolder>() {

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        holder.bind(listGifs[position])
    }

    override fun getItemCount(): Int {
        return listGifs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val v = parent.inflate(R.layout.gif_item)
        return GifHolder(v)
    }

    class GifHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val gifView: RatioImageView? = itemView?.gif_image

        fun bind(gif: GiphyGif) {
            gifView?.setHeightRatio(gif.images.fixedHeight.getHeightScale())
            gifView?.loadFromUrl(gif.images.fixedHeight.url)
        }

    }

}