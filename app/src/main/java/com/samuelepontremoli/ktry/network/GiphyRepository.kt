package com.samuelepontremoli.ktry.network

import com.samuelepontremoli.ktry.utils.GIPHY_KEY
import io.reactivex.Flowable

/**
 * Created by samuele on 01/07/17.
 * Repository class for the Giphy API
 */

class GiphyRepository(val apiService: GiphyService) {

    fun getTrending(offset: Int, rating: String = "g"): Flowable<getGiphyTrending> {
        return apiService.getTrendingGifs(GIPHY_KEY, offset, rating)
    }

    fun getStickers(offset: Int, rating: String = "g"): Flowable<getGiphyTrendingStickers> {
        return apiService.getTrendingStickers(GIPHY_KEY, offset, rating)
    }

    fun getSearchResults(query: String, offset: Int = 0, rating: String = "g"): Flowable<getGiphySearch> {
        return apiService.getGifSearchResults(GIPHY_KEY, query, offset, rating)
    }

}