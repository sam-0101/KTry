package com.samuelepontremoli.ktry.network

import com.samuelepontremoli.ktry.utils.GIPHY_KEY
import io.reactivex.Flowable

/**
 * Created by samuele on 01/07/17.
 * Repository class for the Giphy API
 */

class GiphyRepository(val apiService: GiphyService) {

    fun getTrending(offset: Int): Flowable<getGiphyTrending> {
        return apiService.getTrendingGifs(GIPHY_KEY, offset)
    }

    fun getStickers(offset: Int): Flowable<getGiphyTrendingStickers> {
        return apiService.getTrendingStickers(GIPHY_KEY, offset)
    }

    fun getSearchResults(query: String): Flowable<getGiphySearch> {
        return apiService.getGifSearchResults(GIPHY_KEY, query)
    }

}