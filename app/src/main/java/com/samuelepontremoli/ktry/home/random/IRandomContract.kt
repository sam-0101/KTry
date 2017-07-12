package com.samuelepontremoli.ktry.home.random

import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.commons.BaseView
import com.samuelepontremoli.ktry.network.GiphyGif

/**
 * Created by samuele on 08/07/17.
 * Contract between Random Gifs View and Presenter (MVP)
 */
interface IRandomContract {

    interface IRandomView : BaseView<IRandomPresenter> {

        fun onRandomLoadedSuccess(list: List<GiphyGif>)

        fun onRandomLoadedFailure(error: Throwable)

        fun onRandomLoadedComplete()

        fun showLoading()

        fun hideLoading()

        fun hideError()

        fun showError()

    }

    interface IRandomPresenter : BasePresenter {

        fun loadRandom()

    }

}