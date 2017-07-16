package com.samuelepontremoli.ktry.ui.trending

import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 08/07/17.
 * Trending Gifs presenter
 */
class TrendingPresenter(val view: ITrendingContract.ITrendingView) : ITrendingContract.ITrendingPresenter {

    private val TAG = "TrendingPresenter"

    private val subscriptions: CompositeDisposable

    private val logger = AnkoLogger(TAG)

    private var offset = 0

    private val filesPerDownload = 25

    init {
        subscriptions = CompositeDisposable()
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        loadTrending()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadTrending() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getTrending(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    view.onTrendingLoadedSuccess(data)
                }, {
                    error ->
                    view.onTrendingLoadedFailure(error)
                }, {
                    view.onTrendingLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                    view.enableMoreItemsLoading()
                    offset += filesPerDownload + 1
                })

        subscriptions.add(trendingFlow)
    }

    override fun refreshTrending() {
        offset = 0
        view.emptyTrending()
        loadTrending()
    }

}