package com.samuelepontremoli.ktry.home.stickers

import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

/**
 * Created by s.pontremoli on 12/07/2017.
 * Trending Stickers Presenter
 */
class StickersPresenter(val view: IStickersContract.IStickersView): IStickersContract.IStickersPresenter {

    private val TAG = "StickersPresenter"

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private val logger = AnkoLogger(TAG)

    private var offset = 0

    private val filesPerDownload = 25

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        loadStickers()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun loadStickers() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getStickers(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    view.onStickersLoadedSuccess(data)
                }, {
                    error ->
                    view.onStickersLoadedFailure(error)
                }, {
                    view.onStickersLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                    view.enableMoreItemsLoading()
                })

        subscriptions.add(trendingFlow)
    }

    override fun loadMoreStickers() {
        offset += filesPerDownload + 1
        loadStickers()
    }

    override fun refreshStickers() {
        offset = 0
        view.emptyStickers()
        loadStickers()
    }

}