package com.samuelepontremoli.ktry.ui.random

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.ui.GifsListAdapter
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.utils.makeGone
import com.samuelepontremoli.ktry.utils.makeVisible
import kotlinx.android.synthetic.main.fragment_gifs.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import org.jetbrains.anko.AnkoLogger

/**
 * Created by s.pontremoli on 12/07/2017.
 * Random Gifs Fragment
 */
class RandomFragment : Fragment(), IRandomContract.IRandomView {

    var presenter: RandomPresenter? = null

    private var randomAdapter: GifsListAdapter? = null

    private val logger = AnkoLogger(TAG)

    companion object {
        val TAG = "RandomFragment"
        fun newInstance(): RandomFragment {
            return RandomFragment()
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
        mainRecycler.setItemViewCacheSize(20)
        mainRecycler.isDrawingCacheEnabled = true
        mainRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        mainRecycler.layoutManager = manager
        swipeRefreshLayout.setOnRefreshListener { presenter?.loadRandom() }
        randomAdapter = GifsListAdapter(mutableListOf())
        mainRecycler.adapter = randomAdapter
    }

    override fun onRandomLoadedSuccess(list: List<GiphyGif>) {
        randomAdapter?.setList(list as MutableList<GiphyGif>)
    }

    override fun onRandomLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onRandomLoadedComplete() {
        mainRecycler.adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun hideError() {
        errorView.makeGone()
    }

    override fun showError() {
        errorView.makeVisible()
    }

    override fun setPresenter(presenter: IRandomContract.IRandomPresenter) {
        this.presenter = presenter as RandomPresenter
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