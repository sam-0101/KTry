package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.makeGone
import com.samuelepontremoli.ktry.utils.makeVisible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import org.jetbrains.anko.AnkoLogger

/**
 * Created by s.pontremoli on 06/07/2017.
 * Trending Gifs Fragment
 */

class TrendingFragment : Fragment(), ITrendingContract.ITrendingView {

    private val TAG = "TrendingView"

    var presenter: TrendingPresenter? = null

    private var trendingAdapter: TrendingAdapter? = null

    private val logger = AnkoLogger(TAG)

    companion object {
        fun newInstance(): TrendingFragment {
            return TrendingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        mainRecycler.setItemViewCacheSize(20)
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mainRecycler.layoutManager = manager
        swipeRefreshLayout.setOnRefreshListener { presenter?.loadTrending() }
        trendingAdapter = TrendingAdapter(mutableListOf())
        mainRecycler.adapter = trendingAdapter
    }

    override fun onTrendingLoadedSuccess(list: List<GiphyGif>) {
        trendingAdapter?.setList(list as MutableList<GiphyGif>)
    }

    override fun onTrendingLoadedFailure(error: Throwable) {
        error.printStackTrace()
        errorView.makeVisible()
    }

    override fun onTrendingLoadedComplete() {
        mainRecycler.adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        loadingView.makeVisible()
    }

    override fun hideLoading() {
        loadingView.makeGone()
    }

    override fun onResume() {
        super.onResume()
        presenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter?.unsubscribe()
    }

    override fun setPresenter(presenter: ITrendingContract.ITrendingPresenter) {
        this.presenter = presenter as TrendingPresenter
    }

}