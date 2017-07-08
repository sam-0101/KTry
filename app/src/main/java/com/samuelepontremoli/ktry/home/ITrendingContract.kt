package com.samuelepontremoli.ktry.home

import com.samuelepontremoli.ktry.network.GiphyGif

/**
 * Created by samuele on 08/07/17.
 * Contract between trending view and presenter
 */
interface ITrendingContract {

    interface ITrendingView {

        fun onTrendingLoadedSuccess(list: List<GiphyGif>)

        fun onTrendingLoadedFailure(error: Throwable)

        fun refreshTrending()

    }

    interface ITrendingPresenter {

        fun loadTrending()

    }

}