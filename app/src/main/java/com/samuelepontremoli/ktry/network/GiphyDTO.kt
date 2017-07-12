package com.samuelepontremoli.ktry.network

import com.google.gson.annotations.SerializedName

/**
 * Created by samuele on 01/07/17.
 * DTO items for the Giphy API
 */

data class getGiphyTrending(
        @SerializedName("data") val data: List<GiphyGif>,
        @SerializedName("meta") val meta: GiphyMeta,
        @SerializedName("pagination") val pagination: GiphyPagination
)

data class getGiphySearch(
        @SerializedName("data") val data: List<GiphyGif>,
        @SerializedName("meta") val meta: GiphyMeta,
        @SerializedName("pagination") val pagination: GiphyPagination
)


data class getGiphyTrendingStickers(
        @SerializedName("data") val data: List<GiphyGif>,
        @SerializedName("meta") val meta: GiphyMeta
)

data class GiphyGif(
        @SerializedName("id") val id: String,
        @SerializedName("slug") val slug: String,
        @SerializedName("url") val url: String,
        @SerializedName("bitly_url") val bitlyUrl: String,
        @SerializedName("embed_url") val embedUrl: String,
        @SerializedName("username") val username: String,
        @SerializedName("source") val source: String,
        @SerializedName("rating") val rating: String,
        @SerializedName("caption") val caption: String,
        @SerializedName("trending_datetime") val trendingDatetime: String,
        @SerializedName("images") val images: GiphyImages
)

data class GiphyImages(
        @SerializedName("fixed_height") val fixedHeight: GiphyImage,
        @SerializedName("fixed_height_small") val fixedHeightSmall: GiphyImage,
        @SerializedName("preview_gif") val previewGif: GiphyImage,
        @SerializedName("downsized") val downsizedGif: GiphyImage
)

data class GiphyImage(
        @SerializedName("url") val url: String,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("size") val size: Int
) {
    fun getHeightScale(): Float {
        return height.toFloat() / width.toFloat()
    }
}

data class GiphyMeta(
        @SerializedName("status") val status: Int,
        @SerializedName("msg") val msg: String
)

data class GiphyPagination(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("count") val count: Int,
        @SerializedName("offset") val offset: Int
)