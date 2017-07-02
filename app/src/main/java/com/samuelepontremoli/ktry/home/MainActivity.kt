package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by samuele on 01/07/17.
 * Main class
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()

        callGifs()
    }

    private fun callGifs() {
        //Set api repository
        val giphyRepository = GiphyRepositoryProvider.provideGiphyRepository()

        //Subscribe to repository call
        val trendingFlow = giphyRepository.getTrending() //val makes reference final
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        trendingFlow.subscribe({
            (data) ->
            handleResponse(data)
        }, {
            error ->
            handleError(error)
        })
    }

    private fun initUi() {
        main_recycler.setHasFixedSize(true)
        main_recycler.setItemViewCacheSize(20)
        main_recycler.isDrawingCacheEnabled = true
        main_recycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    private fun handleResponse(listGifs: List<GiphyGif>) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        main_recycler.layoutManager = manager
        main_recycler.adapter = HomeAdapter(listGifs)
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
    }

}