package com.samuelepontremoli.ktry.home

import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.commons.BaseView
import com.samuelepontremoli.ktry.network.GiphyGif

/**
 * Created by samuele on 08/07/17.
 * Contract between Trending Gifs View and Presenter (MVP)
 */
interface ITrendingContract {

    interface ITrendingView : BaseView<ITrendingPresenter> {

        fun onTrendingLoadedSuccess(list: List<GiphyGif>)

        fun onTrendingLoadedFailure(error: Throwable)

        fun onTrendingLoadedComplete()

        fun showLoading()

        fun hideLoading()

    }

    interface ITrendingPresenter : BasePresenter {

        fun loadTrending()

    }

}