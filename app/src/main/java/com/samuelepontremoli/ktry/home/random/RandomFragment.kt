package com.samuelepontremoli.ktry.home.random

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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import org.jetbrains.anko.AnkoLogger

/**
 * Created by s.pontremoli on 12/07/2017.
 * Random Gifs Fragment
 */
class RandomFragment : Fragment(), IRandomContract.IRandomView {

    var presenter: RandomPresenter? = null

    private var randomAdapter: HomeListAdapter? = null

    private val logger = AnkoLogger(TAG)

    companion object {
        val TAG = "RandomFragment"
        fun newInstance(): RandomFragment {
            return RandomFragment()
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
        swipeRefreshLayout.setOnRefreshListener { presenter?.loadRandom() }
        randomAdapter = HomeListAdapter(mutableListOf())
        mainRecycler.adapter = randomAdapter
    }

    override fun onRandomLoadedSuccess(list: List<GiphyGif>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRandomLoadedFailure(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRandomLoadedComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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