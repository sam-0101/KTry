package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.samuelepontremoli.ktry.NUM_CACHED_VIEWS
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.network.GiphyGif
import com.samuelepontremoli.ktry.network.GiphyRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by samuele on 01/07/17.
 * Main class
 */
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var gifRecycler: RecyclerView

    private var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.main_toolbar) as Toolbar
        gifRecycler = findViewById(R.id.main_recycler) as RecyclerView
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

        initDrawer()

        initUi()

        callGifs()
    }

    private fun initDrawer() {
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initUi() {
        //main_recycler.setHasFixedSize(true)
        gifRecycler.setItemViewCacheSize(NUM_CACHED_VIEWS)
        gifRecycler.isDrawingCacheEnabled = true
        gifRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
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
                    gifRecycler.adapter.notifyDataSetChanged()
                })

        subscriptions.add(trendingFlow)
    }

    private fun handleResponse(listGifs: List<GiphyGif>) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        gifRecycler.layoutManager = manager
        gifRecycler.adapter = HomeAdapter(listGifs)
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}