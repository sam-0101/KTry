package com.samuelepontremoli.ktry.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.ui.GifsListAdapter
import com.samuelepontremoli.ktry.utils.customs.InfiniteScrollListener
import com.samuelepontremoli.ktry.utils.makeGone
import com.samuelepontremoli.ktry.utils.makeVisible
import kotlinx.android.synthetic.main.fragment_gifs.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 16/07/17.
 * Gifs Search Fragment
 */
class GifsSearchFragment : Fragment(), IGifsSearchContract.IGifsSearchView {

    var presenter: GifsSearchPresenter? = null

    private var searchAdapter: GifsListAdapter? = null

    private val logger = AnkoLogger(TAG)

    private var infiniteScrollListener: InfiniteScrollListener? = null

    companion object {
        val TAG = "GifsSearchFragment"
        fun newInstance(): GifsSearchFragment {
            return GifsSearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_gifs, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        errorView.makeGone()
        loadingView.makeGone()
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        mainRecycler.layoutManager = manager
        swipeRefreshLayout.setOnRefreshListener { presenter?.refreshGifsSearch() }
        searchAdapter = GifsListAdapter(mutableListOf())
        mainRecycler.adapter = searchAdapter
        infiniteScrollListener = InfiniteScrollListener({ presenter?.loadGifsSearch() }, manager)
        mainRecycler.addOnScrollListener(infiniteScrollListener)
    }

    override fun onGifsSearchLoadedSuccess(list: List<GiphyGif>) {
        searchAdapter?.setList(list as MutableList<GiphyGif>)
    }

    override fun onGifsSearchLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onGifsSearchLoadedComplete() {
        mainRecycler.adapter.notifyDataSetChanged()
    }

    override fun emptyGifsSearch() {
        searchAdapter?.clearItems()
        searchAdapter?.notifyDataSetChanged()
    }

    override fun enableMoreItemsLoading() {
        infiniteScrollListener?.loading = true
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

    override fun setPresenter(presenter: IGifsSearchContract.IGifsSearchPresenter) {
        this.presenter = presenter as GifsSearchPresenter
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