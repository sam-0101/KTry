package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.commons.BaseActivity
import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.home.random.RandomFragment
import com.samuelepontremoli.ktry.home.stickers.StickersFragment
import com.samuelepontremoli.ktry.home.stickers.StickersPresenter
import com.samuelepontremoli.ktry.home.trending.TrendingFragment
import com.samuelepontremoli.ktry.home.trending.TrendingPresenter
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 01/07/17.
 * Trending Gifs Activity
 * Handles drawerLayout and bottomNavigationBar
 */
class HomeActivity : BaseActivity() {

    private val TAG = "HomeActivity"

    private var presenter: BasePresenter? = null

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
    private val bottomNavigation: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()

        initBottomNavigation()

        val trendingFragment = TrendingFragment.newInstance()

        if (presenter == null) {
            presenter = TrendingPresenter(trendingFragment)
        }

        changeFragment(trendingFragment, true, TrendingFragment.TAG)
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_trending -> {
                    switchNavFragment(TrendingFragment.TAG)
                }
                R.id.action_random -> {
                    switchNavFragment(RandomFragment.TAG)
                }
                R.id.action_stickers -> {
                    switchNavFragment(StickersFragment.TAG)
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun switchNavFragment(tag: String): Boolean {
        val frag = supportFragmentManager.findFragmentByTag(tag)
        if (frag == null) {
            when (tag) {
                TrendingFragment.TAG -> {
                    val trendingFragment = TrendingFragment.newInstance()
                    presenter = TrendingPresenter(trendingFragment)
                    changeFragment(trendingFragment, true, tag)
                }
                RandomFragment.TAG -> {
                    val randomFragment = RandomFragment.newInstance()
                    changeFragment(randomFragment, true, tag)
                }
                StickersFragment.TAG -> {
                    val stickersFragment = StickersFragment.newInstance()
                    presenter = StickersPresenter(stickersFragment)
                    changeFragment(stickersFragment, true, tag)
                }
            }
        } else {
            return false
        }
        return true
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