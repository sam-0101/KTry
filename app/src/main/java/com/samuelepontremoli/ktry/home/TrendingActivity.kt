package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import com.samuelepontremoli.ktry.commons.BaseActivity
import com.samuelepontremoli.ktry.R

/**
 * Created by samuele on 01/07/17.
 * Main class
 */
class TrendingActivity : BaseActivity() {

    private var presenter: TrendingPresenter? = null

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

        presenter = TrendingPresenter(trendingFragment)

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