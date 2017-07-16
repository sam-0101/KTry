package com.samuelepontremoli.ktry.home

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.customs.RatioImageView
import com.samuelepontremoli.ktry.utils.inflate
import com.samuelepontremoli.ktry.utils.loadFromUrl
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 01/07/17.
 * Trending Gifs Adapter
 */
class HomeListAdapter(var listGifs: MutableList<GiphyGif>, val VIEW_TYPE: Int = 1) : RecyclerView.Adapter<HomeListAdapter.GiphyHolder>() {

    private val logger = AnkoLogger("HomeListAdapter")

    companion object {
        val TYPE_GIF: Int = 1
        val TYPE_STICKER: Int = 2
    }

    override fun onBindViewHolder(holder: GiphyHolder, position: Int) {
        holder.bind(listGifs[position])
    }

    override fun getItemCount(): Int {
        return listGifs.size
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyHolder {
        when (viewType) {
            TYPE_GIF -> {
                return GifHolder(parent.inflate(R.layout.item_gif))
            }
            TYPE_STICKER -> {
                return StickerHolder(parent.inflate(R.layout.item_sticker))
            }
            else -> return GifHolder(parent.inflate(R.layout.item_gif))
        }
    }

    fun setList(list: MutableList<GiphyGif>) {
        listGifs.addAll(list)
    }

    fun clearItems() {
        listGifs.clear()
    }

    abstract class GiphyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(gif: GiphyGif)
    }

    class GifHolder(itemView: View?) : GiphyHolder(itemView) {

        val gifView: RatioImageView? = itemView?.findViewById(R.id.gifImage)

        override fun bind(gif: GiphyGif) {
            gifView?.setHeightRatio(gif.images.fixedHeightSmall.getHeightScale())
            gifView?.loadFromUrl(gif.images.fixedHeightSmall.url)
        }

    }

    class StickerHolder(itemView: View?) : GiphyHolder(itemView) {

        val stickerView: RatioImageView? = itemView?.findViewById(R.id.stickerImage)

        override fun bind(gif: GiphyGif) {
            stickerView?.setHeightRatio(gif.images.fixedHeight.getHeightScale())
            stickerView?.loadFromUrl(gif.images.fixedHeight.url)
        }

    }

}