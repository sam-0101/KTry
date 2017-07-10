package com.samuelepontremoli.ktry.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.utils.inflate
import com.samuelepontremoli.ktry.utils.loadFromUrl
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.views.RatioImageView
import kotlinx.android.synthetic.main.gif_item.view.*

/**
 * Created by samuele on 01/07/17.
 * Home Adapter
 */
class TrendingAdapter(val listGifs: List<GiphyGif>) : RecyclerView.Adapter<TrendingAdapter.GifHolder>() {

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
            gifView?.setHeightRatio(gif.images.fixedHeightSmall.getHeightScale())
            gifView?.loadFromUrl(gif.images.fixedHeightSmall.url)
        }

    }

}