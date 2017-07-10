package com.samuelepontremoli.ktry.home

import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by samuele on 08/07/17.
 * Trending gifs presenter
 */
class TrendingPresenter(val view: ITrendingContract.ITrendingView) : ITrendingContract.ITrendingPresenter {

    private var subscriptions = CompositeDisposable()

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        loadTrending()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadTrending() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    view.onTrendingLoadedSuccess(data)
                }, {
                    error ->
                    view.onTrendingLoadedFailure(error)
                }, {
                    view.refreshTrending()
                })

        subscriptions.add(trendingFlow)
    }

}