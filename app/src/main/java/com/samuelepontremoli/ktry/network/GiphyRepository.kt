package com.samuelepontremoli.ktry.network

import com.samuelepontremoli.ktry.commons.GIPHY_KEY
import io.reactivex.Flowable

/**
 * Created by samuele on 01/07/17.
 * Repository class for the Giphy API
 */

class GiphyRepository(val apiService: GiphyService) {
    fun getTrending(): Flowable<getGiphyTrending> {
        return apiService.getTrending(GIPHY_KEY)
    }
}