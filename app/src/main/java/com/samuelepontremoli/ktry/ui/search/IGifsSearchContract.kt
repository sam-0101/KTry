package com.samuelepontremoli.ktry.ui.search

import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.commons.BaseView
import com.samuelepontremoli.ktry.network.GiphyGif

/**
 * Created by samuele on 16/07/17.
 * Contract between Gifs Search View and Presenter (MVP)
 */
interface IGifsSearchContract {

    interface IGifsSearchView : BaseView<IGifsSearchPresenter> {

        fun onGifsSearchLoadedSuccess(list: List<GiphyGif>)

        fun onGifsSearchLoadedFailure(error: Throwable)

        fun onGifsSearchLoadedComplete()

        fun emptyGifsSearch()

        fun enableMoreItemsLoading()

        fun showLoading()

        fun hideLoading()

        fun hideError()

        fun showError()

    }

    interface IGifsSearchPresenter : BasePresenter {

        fun loadGifsSearch()

        fun refreshGifsSearch()

    }

}