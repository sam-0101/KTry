package com.samuelepontremoli.ktry.home.random

import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

/**
 * Created by s.pontremoli on 12/07/2017.
 * Random Gifs Presenter
 */
class RandomPresenter(val view: IRandomContract.IRandomView) : IRandomContract.IRandomPresenter {

    private val TAG = "RandomPresenter"

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private val logger = AnkoLogger(TAG)

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        loadRandom()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadRandom() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getSearchResults("asdf")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    view.onRandomLoadedSuccess(data)
                }, {
                    error ->
                    view.onRandomLoadedFailure(error)
                }, {
                    view.onRandomLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                })

        subscriptions.add(trendingFlow)
    }

}