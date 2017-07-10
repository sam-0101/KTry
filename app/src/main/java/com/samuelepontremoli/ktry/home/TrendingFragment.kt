package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by s.pontremoli on 06/07/2017.
 * Trending gifs Fragment
 */

class TrendingFragment : Fragment(), ITrendingContract.ITrendingView {

    private var presenter = TrendingPresenter(this)

    companion object {
        fun newInstance(): TrendingFragment {
            return TrendingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        mainRecycler.setItemViewCacheSize(20)
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    override fun onTrendingLoadedSuccess(list: List<GiphyGif>) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mainRecycler.layoutManager = manager
        mainRecycler.adapter = TrendingAdapter(list)
    }

    override fun onTrendingLoadedFailure(error: Throwable) {
        error.printStackTrace()
    }

    override fun refreshTrending() {
        mainRecycler.adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun setPresenter(presenter: ITrendingContract.ITrendingPresenter) {
        this.presenter = presenter as TrendingPresenter
    }

}