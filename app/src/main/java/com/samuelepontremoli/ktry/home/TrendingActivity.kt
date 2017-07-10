package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.commons.BaseActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by samuele on 01/07/17.
 * Trending Gifs Activity
 * Handles drawerlayout
 */
class TrendingActivity : BaseActivity() {

    private val TAG = "TrendingActivity"

    private var presenter: TrendingPresenter? = null

    private val logger = AnkoLogger(TAG)

    val drawerLayout: DrawerLayout by lazy {
        findViewById(R.id.drawer_layout) as DrawerLayout
    }
    private val toolbar: Toolbar by lazy {
        findViewById(R.id.main_toolbar) as Toolbar
    }
    private val toggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()

        val trendingFragment = TrendingFragment.newInstance()

        if (presenter == null) {
            presenter = TrendingPresenter(trendingFragment)
        }

        changeFragment(trendingFragment, true)
    }

    private fun initDrawer() {
        setSupportActionBar(toolbar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}