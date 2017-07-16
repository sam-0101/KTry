package com.samuelepontremoli.ktry.ui.search

import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 16/07/17.
 * Gifs Search Presenter
 */
class GifsSearchPresenter(val view: IGifsSearchContract.IGifsSearchView, var query: String = "") : IGifsSearchContract.IGifsSearchPresenter {

    private val TAG = "GifsSearchPresenter"

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
        loadGifsSearch()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadGifsSearch() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        val gifsSearchFlow = giphyRepository.getSearchResults(query, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    view.onGifsSearchLoadedSuccess(data)
                }, {
                    error ->
                    view.onGifsSearchLoadedFailure(error)
                }, {
                    view.onGifsSearchLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                    view.enableMoreItemsLoading()
                    offset += filesPerDownload + 1
                })

        subscriptions.add(gifsSearchFlow)
    }

    override fun refreshGifsSearch() {
        offset = 0
        view.emptyGifsSearch()
        loadGifsSearch()
    }

}