package com.samuelepontremoli.ktry.home.trending

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.home.HomeListAdapter
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

    var presenter: TrendingPresenter? = null

    private var trendingAdapter: HomeListAdapter? = null

    private val logger = AnkoLogger(TAG)

    companion object {
        val TAG = "TrendingView"
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
        errorView.makeGone()
        loadingView.makeGone()
        mainRecycler.setItemViewCacheSize(20)
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        mainRecycler.layoutManager = manager
        swipeRefreshLayout.setOnRefreshListener { presenter?.loadTrending() }
        trendingAdapter = HomeListAdapter(mutableListOf())
        mainRecycler.adapter = trendingAdapter
    }

    override fun onTrendingLoadedSuccess(list: List<GiphyGif>) {
        trendingAdapter?.setList(list as MutableList<GiphyGif>)
    }

    override fun onTrendingLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onTrendingLoadedComplete() {
        mainRecycler.adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showError() {
        errorView.makeVisible()
    }

    override fun hideError() {
        errorView.makeGone()
    }

    override fun setPresenter(presenter: ITrendingContract.ITrendingPresenter) {
        this.presenter = presenter as TrendingPresenter
    }

    override fun onResume() {
        super.onResume()
        presenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter?.unsubscribe()
    }

}