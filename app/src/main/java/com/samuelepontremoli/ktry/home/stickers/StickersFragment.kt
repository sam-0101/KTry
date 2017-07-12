package com.samuelepontremoli.ktry.home.stickers

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
 * Created by s.pontremoli on 12/07/2017.
 */
class StickersFragment : Fragment(), IStickersContract.IStickersView {

    var presenter: StickersPresenter? = null

    private var stickersAdapter: HomeListAdapter? = null

    private val logger = AnkoLogger(TAG)

    companion object {
        val TAG = "StickersFragment"
        fun newInstance(): StickersFragment {
            return StickersFragment()
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
        swipeRefreshLayout.setOnRefreshListener { presenter?.loadStickers() }
        stickersAdapter = HomeListAdapter(mutableListOf())
        mainRecycler.adapter = stickersAdapter
    }

    override fun onStickersLoadedSuccess(list: List<GiphyGif>) {
        stickersAdapter?.setList(list as MutableList<GiphyGif>)
    }

    override fun onStickersLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onStickersLoadedComplete() {
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

    override fun setPresenter(presenter: IStickersContract.IStickersPresenter) {
        this.presenter = presenter as StickersPresenter
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