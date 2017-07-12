package com.samuelepontremoli.ktry.home.stickers

import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.commons.BaseView
import com.samuelepontremoli.ktry.network.GiphyGif

/**
 * Created by samuele on 08/07/17.
 * Contract between Trending Stickers View and Presenter (MVP)
 */
interface IStickersContract {

    interface IStickersView : BaseView<IStickersPresenter> {

        fun onStickersLoadedSuccess(list: List<GiphyGif>)

        fun onStickersLoadedFailure(error: Throwable)

        fun onStickersLoadedComplete()

        fun showLoading()

        fun hideLoading()

        fun hideError()

        fun showError()

    }

    interface IStickersPresenter : BasePresenter {

        fun loadStickers()

    }

}