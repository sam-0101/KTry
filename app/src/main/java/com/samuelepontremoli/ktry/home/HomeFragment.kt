package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by s.pontremoli on 06/07/2017.
 * Home Fragment
 */

class HomeFragment: Fragment() {

    private var subscriptions = CompositeDisposable()

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        callGifs()
    }

    private fun initUi() {
        mainRecycler.setItemViewCacheSize(20)
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    private fun callGifs() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (data) ->
                    handleResponse(data)
                }, {
                    error ->
                    handleError(error)
                }, {
                    mainRecycler.adapter.notifyDataSetChanged()
                })

        subscriptions.add(trendingFlow)
    }

    private fun handleResponse(listGifs: List<GiphyGif>) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mainRecycler.layoutManager = manager
        mainRecycler.adapter = HomeAdapter(listGifs)
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

}