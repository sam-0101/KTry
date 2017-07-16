package com.samuelepontremoli.ktry.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.commons.BaseActivity
import com.samuelepontremoli.ktry.commons.BasePresenter
import com.samuelepontremoli.ktry.ui.random.RandomFragment
import com.samuelepontremoli.ktry.ui.random.RandomPresenter
import com.samuelepontremoli.ktry.ui.search.GifsSearchFragment
import com.samuelepontremoli.ktry.ui.search.GifsSearchPresenter
import com.samuelepontremoli.ktry.ui.stickers.StickersFragment
import com.samuelepontremoli.ktry.ui.stickers.StickersPresenter
import com.samuelepontremoli.ktry.ui.trending.TrendingFragment
import com.samuelepontremoli.ktry.ui.trending.TrendingPresenter
import org.jetbrains.anko.AnkoLogger

/**
 * Created by samuele on 01/07/17.
 * Trending Gifs Activity
 * Handles DrawerLayout, BottomNavigationBar and SearchView
 */
class GifsActivity : BaseActivity() {

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

    companion object {
        val TAG = "GifsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifs)

        initBottomNavigation()

        initDrawer()

        initDefaultFragment()
    }

    private fun initDefaultFragment() {
        val trendingFragment = TrendingFragment.newInstance()
        if (presenter == null) {
            presenter = TrendingPresenter(trendingFragment)
        }
        changeFragment(trendingFragment, true, TrendingFragment.TAG)
    }

    private fun initDrawer() {
        setSupportActionBar(toolbar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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
                    presenter = RandomPresenter(randomFragment)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_toolbar, menu)
        initSearch(menu)
        return true
    }

    private fun initSearch(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.background = ContextCompat.getDrawable(this, R.drawable.side_nav_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchFragment = GifsSearchFragment.newInstance()
                if (query != null) {
                    presenter = GifsSearchPresenter(searchFragment, query)
                }
                changeFragment(searchFragment, true, GifsSearchFragment.TAG)
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
